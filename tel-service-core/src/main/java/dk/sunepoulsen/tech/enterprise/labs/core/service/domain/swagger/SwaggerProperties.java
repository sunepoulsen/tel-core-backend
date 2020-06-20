package dk.sunepoulsen.tech.enterprise.labs.core.service.domain.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties( prefix = "swagger" )
public class SwaggerProperties {
    private String host;
    private String basePath;
    private String title;
    private String description;
    private Contact contact;
    private String license;
    private String licenseUrl;
    private String version;

    public static class Contact {
        private String name;
        private String url;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Contact contact = (Contact) o;
            return Objects.equals(name, contact.name) &&
                Objects.equals(url, contact.url) &&
                Objects.equals(email, contact.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, url, email);
        }

        @Override
        public String toString() {
            return "Contact{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                '}';
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SwaggerProperties that = (SwaggerProperties) o;
        return Objects.equals(host, that.host) &&
            Objects.equals(basePath, that.basePath) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(license, that.license) &&
            Objects.equals(licenseUrl, that.licenseUrl) &&
            Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, basePath, title, description, contact, license, licenseUrl, version);
    }

    @Override
    public String toString() {
        return "SwaggerProperties{" +
            "host='" + host + '\'' +
            ", basePath='" + basePath + '\'' +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", contact=" + contact +
            ", license='" + license + '\'' +
            ", licenseUrl='" + licenseUrl + '\'' +
            ", version='" + version + '\'' +
            '}';
    }
}
