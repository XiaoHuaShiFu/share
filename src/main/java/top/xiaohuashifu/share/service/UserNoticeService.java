package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.UserNoticeDO;
import top.xiaohuashifu.share.pojo.query.UserNoticeQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 用户通知Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface UserNoticeService {

    Result<UserNoticeDO> saveUserNotice(UserNoticeDO userNoticeDO);

    Result<UserNoticeDO> getUserNotice(Integer id);

    Result<PageInfo<UserNoticeDO>> listUserNotices(UserNoticeQuery query);

    Result<Integer> countUserNotices(UserNoticeQuery query);
}
