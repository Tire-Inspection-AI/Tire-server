package hello.capstone.oauth2;

import java.util.Map;

public class GithubUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
    }
    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProfileName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getProfileImagePath() {
        return null;
    }

    @Override
    public String getProfileBirth() {
        return null;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
