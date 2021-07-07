package cn.smbms.eception;

public class MyEception extends Exception{
    // 异常消息
    private String message;

    /**
     *
     */
    public MyEception() {
        super();
    }

    /**
     * @param message
     */
    public MyEception(String message) {
        super();
        this.message = message;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
