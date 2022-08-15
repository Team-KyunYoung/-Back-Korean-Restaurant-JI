package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@ApiModel(value = "회원 정보", description = "아이디, 이메일, 비밀번호 등 회원 정보를 가진 Class")
@Entity(name = "user")  //JPA가 관리하는 클래스. DB 테이블과 매핑할 클래스는 @Entity를 꼭 붙여야만 매핑이 된다.
                        //name : JPA에서 사용할 엔티티의 이름을 지정. 중복되면 안됨.
@Getter //get 필드(해당 데이터를 가져옴) 작성 필요 없이, getUserId() 등의 메서드를 사용할 수 있게 한다.
@Setter
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성 (생성자는 하나 이상 작성해야 하나, 해당 어노테이션으로 퉁치기 가능)
@AllArgsConstructor //어노테이션은 모든 필드 값을 파라미터로 받는 생성자 생성 (위 어노테이션도 그렇지만, 굳이 코드 작성할 수고를 덜어줌)
@Table(name = "USER") //엔티티와 매핑할 테이블을 지정. (name : 매핑할 테이블 이름을 지정)
public class User implements UserDetails {

    @ApiModelProperty(value = "아이디") //Swagger에 표시될 데이타 이름 정의
    @Id //JPA 엔티티 객체의 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 생성 (기본키 생성을 데이터베이스에게 위임하는 방식 = AUTO_INCREMENT)
    @Column(name = "user_number") //객체 필드를 테이블의 컬럼에 매핑 (name : db 컬럼 명)
    private Long userNumber; // JPA(백,프론트)에서 다음 변수명으로 데이타 접근 가능.

    @ApiModelProperty(value = "이메일")           //unique : 중복되면 안됨.
    @Column(name = "user_email", nullable = false, unique = true,  length = 40)
    private String userEmail;

    @ApiModelProperty(value = "비밀번호")
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @ApiModelProperty(value = "별명")                //length : 데이터 최대 크기
    @Column(name = "user_nickname", nullable = false,  length = 20)
    private String userNickname;

    @ApiModelProperty(value = "역할")
    @Column(name = "role", nullable = false)
    @ColumnDefault("false") // @ColumnDefault : default 값 정의
    private boolean role;

    @Builder //디자인패턴중 하나. 객체를 생성하는 방식으로, 더 간단하고 명확하다. (dto에 사용됨)
    public User (String userNickname, String userEmail, String userPassword) {
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    //해당 유저의 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //비밀번호
    @Override
    public String getPassword() {
        return this.userPassword;
    }

    //이메일
    @Override
    public String getUsername() {
        return this.userEmail;
    }

    //계정 만료 여부
    //  true : 만료 안됨
    //  false : 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부
    //  true : 잠기지 않음
    //  false : 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료 여부
    //  true : 만료 안됨
    //  false : 만료
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //사용자 활성화 여부
    //  ture : 활성화
    //  false : 비활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
