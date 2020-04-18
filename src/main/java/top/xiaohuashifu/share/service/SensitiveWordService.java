package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.SensitiveWordDO;
import top.xiaohuashifu.share.pojo.query.SensitiveWordQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface SensitiveWordService {

    Result<SensitiveWordDO> saveSensitiveWord(SensitiveWordDO sensitiveWordDO);

    Result<String> deleteSensitiveWord(Integer id);

    Result<SensitiveWordDO> getSensitiveWord(Integer id);

    Result<PageInfo<SensitiveWordDO>> listSensitiveWords(SensitiveWordQuery query);

    Result<String> filter(String sentence);

    void cachingSensitiveWord();

}
