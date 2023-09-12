package hello.capstone.oauth2;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getProfileName();
    String getProfileImagePath();

    String getProfileBirth();

    String getEmail();
}