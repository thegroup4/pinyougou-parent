package entity;

public class SysLog {
    private Long id;
    private String visitTime;
    private String username;
    private String ip;
    private String method;
    //此处省略getter和setter方法... ...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "id=" + id +
                ", visitTime='" + visitTime + '\'' +
                ", username='" + username + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}