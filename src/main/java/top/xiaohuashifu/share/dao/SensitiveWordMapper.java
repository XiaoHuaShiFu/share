package top.xiaohuashifu.share.dao;

import top.xiaohuashifu.share.pojo.do0.SensitiveWordDO;
import top.xiaohuashifu.share.pojo.query.SensitiveWordQuery;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface SensitiveWordMapper {

    /**
     * 插入敏感词
     * @param sensitiveWordDO 敏感词
     * @return 插入的数量
     */
    int insertSensitiveWord(SensitiveWordDO sensitiveWordDO);

    /**
     * 删除敏感词
     * @param id 编号
     * @return 删除的数量
     */
    int deleteSensitiveWord(Integer id);

    /**
     * 获取敏感词
     * @param id 编号
     * @return 敏感词
     */
    SensitiveWordDO getSensitiveWord(Integer id);

    /**
     * 获取敏感词列表
     * @param query 查询参数
     * @return 敏感词列表
     */
    List<SensitiveWordDO> listSensitiveWords(SensitiveWordQuery query);

    /**
     * 通过敏感词查询数量，也就是判断敏感词存不存在
     * @param sensitiveWord 敏感词
     * @return 敏感词数量
     */
    int countBySensitiveWord(String sensitiveWord);

}
