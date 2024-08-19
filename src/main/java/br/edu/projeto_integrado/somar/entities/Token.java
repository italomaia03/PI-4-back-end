package br.edu.projeto_integrado.somar.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tokens")
@SQLDelete(sql = "UPDATE tokens SET deleted_at = NOW() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class Token extends BaseEntity {
    @Column(name = "access_token", nullable = false)
    private String accessToken;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
