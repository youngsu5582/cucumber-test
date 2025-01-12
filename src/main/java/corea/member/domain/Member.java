package corea.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String username;

    @Column(length = 32768)
    private String thumbnailUrl;

    private String name;

    private String email;

    private boolean isEmailAccepted;

    private String profileLink;

    public Member(String username, String thumbnailUrl, String name, String email, boolean isEmailAccepted) {
        this(null, username, thumbnailUrl, name, email, isEmailAccepted, "");
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isEmailAccepted=" + isEmailAccepted +
                ", profileLink='" + profileLink + '\'' +
                '}';
    }
}
