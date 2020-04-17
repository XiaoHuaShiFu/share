package top.xiaohuashifu.share.util.ftp;


import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * 描述: FTPClientTemplate需要使用的回调接口
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface FTPClientCallback {
    void processFTPRequest(FTPClient ftpClient) throws IOException;
}
