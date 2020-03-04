package http.conf;

public enum EncodeConf {
//    byte a= 1;
    UTF8(1),gb2312(2);

    private byte code;
    EncodeConf(int code){
        this.code = Byte.valueOf(code+"");
    }

    public byte getCode() {
        return code;
    }
}
