package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.dao.SensitiveWordMapper;
import top.xiaohuashifu.share.pojo.do0.SensitiveWordDO;
import top.xiaohuashifu.share.pojo.query.SensitiveWordQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.CacheService;
import top.xiaohuashifu.share.service.SensitiveWordService;
import top.xiaohuashifu.share.service.constant.SensitiveWordConstant;

import java.util.List;
import java.util.Set;

/**
 * 描述: 敏感词服务
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl implements SensitiveWordService {

    private static final Logger logger= LoggerFactory.getLogger(SensitiveWordServiceImpl.class);

    private final SensitiveWordMapper sensitiveWordMapper;

    private final CacheService cacheService;

    public SensitiveWordServiceImpl(SensitiveWordMapper sensitiveWordMapper, CacheService cacheService) {
        this.sensitiveWordMapper = sensitiveWordMapper;
        this.cacheService = cacheService;
    }

    /**
     * 增加敏感词
     * @param sensitiveWordDO 敏感词
     * @return SensitiveWordDO
     */
    @Override
    public Result<SensitiveWordDO> saveSensitiveWord(SensitiveWordDO sensitiveWordDO) {
        // 检查敏感词存不存在
        int count = sensitiveWordMapper.countBySensitiveWord(sensitiveWordDO.getSensitiveWord());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Insert sensitiveWord fail due to the sensitiveWord={0} has been exists.",
                    sensitiveWordDO.getSensitiveWord());
        }

        // 插入敏感词
        count = sensitiveWordMapper.insertSensitiveWord(sensitiveWordDO);
        if (count < 1) {
            logger.error("Insert sensitiveWord fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert sensitiveWord fail.");
        }
        return getSensitiveWord(sensitiveWordDO.getId());
    }

    /**
     * 删除敏感词
     * @param id 编号
     * @return Result<String>
     */
    @Override
    public Result<String> deleteSensitiveWord(Integer id) {
        // 查看敏感词存不存
        Result<SensitiveWordDO> result = getSensitiveWord(id);
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The sensitiveWord of id={0} is not exists.", id);
        }

        int count = sensitiveWordMapper.deleteSensitiveWord(id);
        if (count < 1) {
            logger.error("Delete sensitiveWord fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete sensitiveWord fail.");
        }

        return Result.success("Delete sensitiveWord:" + result.getData().getSensitiveWord() + " success.");
    }

    /**
     * 获取敏感词
     * @param id 编号
     * @return 敏感词
     */
    @Override
    public Result<SensitiveWordDO> getSensitiveWord(Integer id) {
        SensitiveWordDO sensitiveWord = sensitiveWordMapper.getSensitiveWord(id);
        if (sensitiveWord == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found. id={0}", id);
        }

        return Result.success(sensitiveWord);
    }

    /**
     * 查询敏感词列表
     * @param query 查询参数
     * @return 敏感词列表
     */
    @Override
    public Result<PageInfo<SensitiveWordDO>> listSensitiveWords(SensitiveWordQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SensitiveWordDO> pageInfo = new PageInfo<>(sensitiveWordMapper.listSensitiveWords(query));
        if (pageInfo.getList().isEmpty()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 对敏感词进行过滤
     * @param sentence 要过滤的句子
     * @return 过滤结果
     */
    @Override
    public Result<String> filter(String sentence) {
        Set<String> sensitiveWordSet = cacheService.smembers(SensitiveWordConstant.KEY_OF_REDIS_FOR_SENSITIVE_WORD);
        for (String sensitiveWord : sensitiveWordSet) {
            if (sentence.contains(sensitiveWord)) {
                return Result.fail(ErrorCode.INVALID_PARAMETER, "The word={0} is sensitive word.", sensitiveWord);
            }
        }
        return Result.success("The sentence is valid.");
    }

    /**
     * 缓存敏感词
     */
    @Override
    public void cachingSensitiveWord() {
        SensitiveWordQuery sensitiveWordQuery = new SensitiveWordQuery();
        sensitiveWordQuery.setPageSize(1000000);
        List<SensitiveWordDO> sensitiveWordDOList = sensitiveWordMapper.listSensitiveWords(sensitiveWordQuery);
        // 删除旧缓存
        cacheService.del(SensitiveWordConstant.KEY_OF_REDIS_FOR_SENSITIVE_WORD);
        // 添加新缓存
        cacheService.sadd(SensitiveWordConstant.KEY_OF_REDIS_FOR_SENSITIVE_WORD,
                sensitiveWordDOList.stream().map(SensitiveWordDO::getSensitiveWord).toArray(String[]::new));
    }

}
