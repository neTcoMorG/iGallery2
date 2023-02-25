package com.igrallery.jun.domain.entity;

import com.igrallery.jun.domain.entity.img.Image;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Gallery {

    public Gallery(User user, String sub) {
        this.user = user;
        this.sub = sub;
        this.backgroundColor = "#000000";
    }
    protected  Gallery() {}

    public void updateBackgroundColor (String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "MEMBER") private User user;
    @Column(name = "SUB") private String sub;
    @Column(name = "CREATED_DATE") @CreatedDate private LocalDateTime createdDate;

    @Column(name = "BACKGROUND_COLOR") private String backgroundColor;

    @Column(name = "THUMBNAIL") private String thumbnail;
    @OneToMany(mappedBy = "gallery") private List<Image> images = new ArrayList<>();

    public boolean isOwner(User user) {
        return this.user.equals(user);
    }
}
