package concept.githubusersearch.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import concept.githubusersearch.model.RepoEntry;

/**
 * DB Entity for {@link RepoEntry}
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Repo {

    @Id private Long id;

    @Index(unique = true) private Integer serverId;
    private String name;
    private String language;
    private String description;

    private long userId; // FK on User
    //

    @Generated(hash = 1228305388)
    public Repo(Long id, Integer serverId, String name, String language,
            String description, long userId) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.language = language;
        this.description = description;
        this.userId = userId;
    }

    @Generated(hash = 1082775620)
    public Repo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getServerId() {
        return this.serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
