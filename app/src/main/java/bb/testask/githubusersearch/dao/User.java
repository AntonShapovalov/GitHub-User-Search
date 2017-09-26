package bb.testask.githubusersearch.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import bb.testask.githubusersearch.model.UserEntry;

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
    private String name;
    private String bio;
    //
    @Generated(hash = 1501475335)
    public User(Long id, String login, Integer serverId, String avatarUrl,
            String name, String bio) {
        this.id = id;
        this.login = login;
        this.serverId = serverId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.bio = bio;
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBio() {
        return this.bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

}
