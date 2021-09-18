package com.jky.verify.common.util;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {


    /**
     * enums 目录
     */
    public final static String ENUMS_PATH = "wofang.common.enums.common";

    /**
     * 环境变量
     */
    public final static String OS_NAME = "os.name";

    public final static String RESULT = "result";

    public final static String ERROR_CODE = "errorCode";

    public final static String ERROR_MSG = "errorMsg";

    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";
    /**
     * 字符串0
     */
    public static final String ZERO = "0";
    /**
     * 字符串1
     */
    public static final String ONE = "1";
    /**
     * 整数0
     */
    public static final int INTEGER_ZERO = 0;
    /**
     * 整数1
     */
    public static final int INTEGER_ONE = 1;

    /**
     * 云平台转到其他平台的操作人名称
     */
    public static final String ALLIANCE_BUSINESS_ADMIN = "allianceBusinessAdmin";


    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * appId的长度
     */
    public static int APP_ID_LENGTH = 19;

    /**
     * appId的前缀
     */
    public static String APP_ID_PREFIX = "wfdc";

    /**
     * AppSecret的长度
     */
    public static int APP_SECRET_LENGTH = 21;
    /**
     * appId参数名
     */
    public static String APPID_KEY = "appId";
    /**
     * sign参数名
     */
    public static String SIGN_KEY = "sign";
    /**
     * nonce参数名
     */
    public static String NONCE_KEY = "nonce";
    /**
     * timestamp参数名
     */
    public static String TIMESTAMP_KEY = "timestamp";
    /**
     * 手机key
     */
    public static String TEL_KEY = "tel";
    /**
     * 默认值
     */
    public static String DEFAULT_KEY = "default";
    /**
     * 房源状态：无效无效
     */
    public static String SOURCE_STATUS_INVALID = "无效无效";
    /**
     * 统计范围内的部门ID的key
     */
    public static String STATISTICAL_DEPT_TYPE = "STATISTICAL_DEPT";
    /**
     * 海澄二手房部
     */
    public static String STATISTICAL_DEPT_HAICHENG_KEY = "haichengershou";
    /**
     * all
     */
    public static String ALL_STR = "all";
    /**
     * 默认密码
     */
    public static String DEFAULT_PASSWORD = "wf123456";
    /**
     * 超级管理员的roleKey
     */
    public static String SUPER_ADMIN_ROLE_KEY = "superAdmin";
    /**
     * 松之光企业key
     */
    public static String SONG_ZHI_GUANG_KEY = "songzhiguang";
}
