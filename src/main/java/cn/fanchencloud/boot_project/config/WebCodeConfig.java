package cn.fanchencloud.boot_project.config;

/**
 * Created by handsome programmer.
 *
 * @author chen
 * @User: chen
 * @Date: 2021/3/11
 * @Time: 9:38
 * @Description: 常用返回码
 */
public class WebCodeConfig {

    /**
     * 继续
     * 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。
     */
    public static final int CODE_CONTINUE = 100;
    /**
     * 切换协议
     * 请求者已要求服务器切换协议，服务器已确认并准备切换。
     */
    public static final int CODE_SWITCH_PROTOCOL = 101;

    /**
     * 成功
     */
    public static final int CODE_SUCCESS = 200;
    /**
     * 已创建
     */
    public static final int CODE_CREATED = 201;
    /**
     * 错误请求
     */
    public static final int CODE_BAD_REQUEST = 400;
    /**
     * 未授权
     */
    public static final int CODE_UNAUTHORIZED = 401;
    /**
     * 禁止
     */
    public static final int CODE_FORBID = 403;
    /**
     * 请求的资源不存在
     */
    public static final int CODE_NOT_FOUND = 404;
    /**
     * 服务器内部错误
     */
    public static final int CODE_SERVER_INTERNAL_ERROR = 500;
    /**
     * 服务不可用
     */
    public static final int CODE_SERVICE_IS_NOT_AVAILABLE = 503;
}
