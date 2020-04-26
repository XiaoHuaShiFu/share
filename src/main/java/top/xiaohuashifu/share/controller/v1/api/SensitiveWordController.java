package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.xiaohuashifu.share.aspect.annotation.AdminLog;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.AdminLogType;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.do0.SensitiveWordDO;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.SensitiveWordQuery;
import top.xiaohuashifu.share.pojo.vo.SensitiveWordVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.SensitiveWordService;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

/**
 * 描述: 敏感词模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/sensitive_words")
@Validated
public class SensitiveWordController {

    private final Mapper mapper;

    private final SensitiveWordService sensitiveWordService;

    @Autowired
    public SensitiveWordController(Mapper mapper, SensitiveWordService sensitiveWordService) {
        this.mapper = mapper;
        this.sensitiveWordService = sensitiveWordService;
    }

    /**
     * 创建敏感词
     * @param sensitiveWordDO 敏感词
     * @return SensitiveWordVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER
     * OPERATION_CONFLICT
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_BLANK
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    // TODO: 2020/4/27 这里如果可以不用注解切面会更好
    @AdminLog(type = AdminLogType.SENSITIVE_WORD_ADD)
    @ErrorHandler
    public Object post(@Validated(GroupPost.class) SensitiveWordDO sensitiveWordDO) {
        Result<SensitiveWordDO> result = sensitiveWordService.saveSensitiveWord(sensitiveWordDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), SensitiveWordVO.class);
    }

    /**
     * 查询敏感词
     * @param query 查询参数
     * @return PageInfo<SensitiveWordVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
//    @TokenAuth(tokenType = {TokenType.ADMIN})
    @ErrorHandler
    public Object get(SensitiveWordQuery query) {
        Result<PageInfo<SensitiveWordDO>> result = sensitiveWordService.listSensitiveWords(query);
        if (!result.isSuccess()) {
            return result;
        }

        PageInfo<SensitiveWordVO> pageInfo = mapper.map(result.getData(), PageInfo.class);
        pageInfo.setList(result.getData().getList().stream()
                .map(sensitiveWordDO -> mapper.map(sensitiveWordDO, SensitiveWordVO.class))
                .collect(Collectors.toList()));
        return pageInfo;
    }

    /**
     * 删除敏感词
     * @param id 敏感词的id
     * @return String
     *
     * @success:
     * HttpStatus.DELETE
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    @AdminLog(type = AdminLogType.SENSITIVE_WORD_DELETE)
    @ErrorHandler
    public Object delete(@PathVariable @Id Integer id) {
        Result<String> result = sensitiveWordService.deleteSensitiveWord(id);
        return !result.isSuccess() ? result : result.getMessage();
    }

    /**
     * 敏感词过滤器
     * @param sentence 句子
     * @return String
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INVALID_PARAMETER
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_BLANK
     */
    @RequestMapping(value="/filter", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    @ErrorHandler
    public Object filter(@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The sentence must be not blank.")
                                     String sentence) {
        Result<String> result = sensitiveWordService.filter(sentence);
        return !result.isSuccess() ? result : result.getData();
    }

}
