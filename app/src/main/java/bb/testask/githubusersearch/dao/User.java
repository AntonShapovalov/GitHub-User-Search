package bb.testask.githubusersearch.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import bb.testask.githubusersearch.model.UserEntry;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * DB Entity for {@link UserEntry}
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class User {

    @Id private Long id;

    private String login;
    @Index(unique = true) private Integer serverId;
    private String avatarUrl;
    //
    @Generated(hash = 1861699618)
    public User(Long id, String login, Integer serverId, String avatarUrl) {
        this.id = id;
        this.login = login;
        this.serverId = serverId;
        this.avatarUrl = avatarUrl;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public Integer getServerId() {
        return this.serverId;
    }
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
