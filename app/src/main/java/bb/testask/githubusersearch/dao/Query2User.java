package bb.testask.githubusersearch.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Many-to-Many Query-to-User relation
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Query2User {

    @Id private Long id;

    private Long queryId; // FK on Query
    private Long userId; // FK on User
    @Generated(hash = 1417671705)
    public Query2User(Long id, Long queryId, Long userId) {
        this.id = id;
        this.queryId = queryId;
        this.userId = userId;
    }
    @Generated(hash = 1465965391)
    public Query2User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getQueryId() {
        return this.queryId;
    }
    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
