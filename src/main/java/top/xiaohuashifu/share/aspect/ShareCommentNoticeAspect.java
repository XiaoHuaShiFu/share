package top.xiaohuashifu.share.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.constant.UserNoticeType;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.do0.UserNoticeDO;
import top.xiaohuashifu.share.pojo.vo.ShareCommentVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.UserNoticeService;
import top.xiaohuashifu.share.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 分享评论日志切面
 */
@Aspect
@Component
public class ShareCommentNoticeAspect {

    private final UserNoticeService userNoticeService;

    private final UserService userService;

    private final ShareService shareService;

    private final Gson gson;

    /**
     * 在通知里最长的评论内容
     */
    private final static int MAX_NOTICE_CONTENT_LENGTH = 10;

	@Autowired
	public ShareCommentNoticeAspect(UserNoticeService userNoticeService, UserService userService,
                                    ShareService shareService, Gson gson) {
        this.userNoticeService = userNoticeService;
        this.userService = userService;
        this.shareService = shareService;
        this.gson = gson;
    }

	/**
	 * 分享评论通知切面
	 *
	 * @param joinPoint ProceedingJoinPoint
     * @return Object
	 */
    @Around(value = "execution(* top.xiaohuashifu.share.controller.v1.api.ShareCommentController.post(..))")
	public Object notice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result =  joinPoint.proceed();

		// 消息通知
        if (result instanceof ShareCommentVO) {
            ShareCommentVO shareCommentVO = (ShareCommentVO) result;
            // 获取评论的用户
            Result<UserDO> user = userService.getUser(shareCommentVO.getUserId());
            // 被评论的用户的id
            Result<ShareDO> share = shareService.getShare(shareCommentVO.getShareId());
            int userId = share.getData().getUserId();

            // 构造UserNotice
            UserNoticeDO userNoticeDO = new UserNoticeDO();
            userNoticeDO.setUserId(userId);
            userNoticeDO.setType(UserNoticeType.SHARE_COMMENT);
            userNoticeDO.setNoticeTime(new Date());
            userNoticeDO.setContent("用户@" + user.getData().getNickName() + "评论了你的分享#"
                    + share.getData().getContent().substring(0,
                    share.getData().getContent().length() < MAX_NOTICE_CONTENT_LENGTH
                    ? share.getData().getContent().length() : MAX_NOTICE_CONTENT_LENGTH)
                    + "...#   //@" + user.getData().getNickName() + ": "
                    + shareCommentVO.getContent().substring(0,
                    shareCommentVO.getContent().length() < MAX_NOTICE_CONTENT_LENGTH
                    ? shareCommentVO.getContent().length() : MAX_NOTICE_CONTENT_LENGTH)  + "...");

            Map<String, Object> keyValue = new HashMap<>();
            keyValue.put("shareId", shareCommentVO.getShareId());
            keyValue.put("shareCommentId", shareCommentVO.getId());
            keyValue.put("avatarUrl", user.getData().getAvatarUrl());
            keyValue.put("nickName", user.getData().getNickName());
            userNoticeDO.setKeyValue(gson.toJson(keyValue));

            userNoticeService.saveUserNotice(userNoticeDO);
        }

		return result;
	}

}
