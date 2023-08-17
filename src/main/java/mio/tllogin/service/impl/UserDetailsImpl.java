package mio.tllogin.service.impl;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private int active;
    private String roles;
    private String permissions;
    private int userType;
    private Date createdate;

    @Builder
    public UserDetailsImpl(Long id, String username, String password, String confirmPassword, String email, int active, String roles, String permissions, int userType, Date createdate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.active = active;
        this.roles = roles;
        this.permissions = permissions;
        this.userType = userType;
        this.createdate = createdate;

        this.active = 1;
    }

    @Builder
    public UserDetailsImpl(UserDetailsImpl userDetailsImpl) {
        this.id = userDetailsImpl.getId();
        this.username = userDetailsImpl.getUsername();
        this.password = userDetailsImpl.getPassword();
        this.confirmPassword = userDetailsImpl.getConfirmPassword();
        this.email = userDetailsImpl.getEmail();
        this.active = userDetailsImpl.getActive();
        this.roles = userDetailsImpl.getRoles();
        this.permissions = userDetailsImpl.getPermissions();
        this.userType = userDetailsImpl.getUserType();
        this.createdate = userDetailsImpl.getCreatedate();

        this.active = 1;
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length()>0){
            return Arrays.asList(this.permissions.split(","));
        }

        return new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public <R> String orElseThrow(Function<? super Exception, ? extends R> mapper) {
        return null;
    }
}
