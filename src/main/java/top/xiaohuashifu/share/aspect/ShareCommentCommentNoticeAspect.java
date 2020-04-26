package top.xiaohuashifu.share.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.constant.UserNoticeType;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.do0.UserNoticeDO;
import top.xiaohuashifu.share.pojo.vo.ShareCommentCommentVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 分享评论的评论日志切面
 */
@Aspect
@Component
public class ShareCommentCommentNoticeAspect {

    private final UserNoticeService userNoticeService;

    private final UserService userService;

    private final ShareCommentService shareCommentService;

    private final ShareCommentCommentService shareCommentCommentService;

    private final Gson gson;

    /**
     * 在通知里最长的评论内容
     */
    private final static int MAX_NOTICE_CONTENT_LENGTH = 10;

	@Autowired
	public ShareCommentCommentNoticeAspect(UserNoticeService userNoticeService, UserService userService,
                                           ShareCommentService shareCommentService,
                                           ShareCommentCommentService shareCommentCommentService, Gson gson) {
        this.userNoticeService = userNoticeService;
        this.userService = userService;
        this.shareCommentService = shareCommentService;
        this.shareCommentCommentService = shareCommentCommentService;
        this.gson = gson;
    }

	/**
	 * 分享评论的评论通知切面
	 *
	 * @param joinPoint ProceedingJoinPoint
     * @return Object
	 */
    @Around(value = "execution(* top.xiaohuashifu.share.controller.v1.api.ShareCommentCommentController.post(..))")
	public Object notice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result =  joinPoint.proceed();

		// 消息通知
        if (result instanceof ShareCommentCommentVO) {
            ShareCommentCommentVO shareCommentCommentVO = (ShareCommentCommentVO) result;
            // 获取评论的用户
            Result<UserDO> user = userService.getUser(shareCommentCommentVO.getUserId());
            // 在什么一级评论下面
            Result<ShareCommentDO> shareComment = shareCommentService.getShareComment(
                    shareCommentCommentVO.getShareCommentId());

            // 这里是被评论用户的id
            int userId;
            String content;
            // 如果有父评论
            if (shareCommentCommentVO.getParentShareCommentCommentId() != 0) {
                userId = shareCommentCommentVO.getParentShareCommentCommentUser().getId();

                Result<ShareCommentCommentDO> shareCommentComment = shareCommentCommentService.getShareCommentComment(
                        shareCommentCommentVO.getParentShareCommentCommentId());
                content = shareCommentComment.getData().getContent().substring(0,
                        shareCommentComment.getData().getContent().length() < MAX_NOTICE_CONTENT_LENGTH ?
                                shareCommentComment.getData().getContent().length() : MAX_NOTICE_CONTENT_LENGTH);
            }
            // 如果没有父评论
            else {
                userId = shareComment.getData().getUserId();
                content = shareComment.getData().getContent().substring(0,
                        shareComment.getData().getContent().length() < MAX_NOTICE_CONTENT_LENGTH ?
                                shareComment.getData().getContent().length() : MAX_NOTICE_CONTENT_LENGTH);
            }
            // 获取对应的share的id
            int shareId = shareComment.getData().getShareId();

            // 构造UserNotice
            UserNoticeDO userNoticeDO = new UserNoticeDO();
            userNoticeDO.setUserId(userId);
            userNoticeDO.setType(UserNoticeType.SHARE_COMMENT_REPLY);
            userNoticeDO.setNoticeTime(new Date());

            userNoticeDO.setContent("用户@" + user.getData().getNickName() + "回复了你的评论#"
                    + content + "...#   //@"  + user.getData().getNickName() + ": " +
                    shareCommentCommentVO.getContent().substring(0,
                            shareCommentCommentVO.getContent().length() < MAX_NOTICE_CONTENT_LENGTH ?
                            shareCommentCommentVO.getContent().length() : MAX_NOTICE_CONTENT_LENGTH) + "...");

            Map<String, Integer> keyValue = new HashMap<>();
            keyValue.put("shareId", shareId);
            keyValue.put("shareCommentId", shareCommentCommentVO.getShareCommentId());
            keyValue.put("parentShareCommentCommentId", shareCommentCommentVO.getParentShareCommentCommentId());
            userNoticeDO.setKeyValue(gson.toJson(keyValue));

            userNoticeService.saveUserNotice(userNoticeDO);
        }

		return result;
	}

}
