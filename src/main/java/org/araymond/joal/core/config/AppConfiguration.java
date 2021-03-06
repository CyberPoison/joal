package org.araymond.joal.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by raymo on 24/01/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppConfiguration {

    private final Long minUploadRate;
    private final Long maxUploadRate;
    private final Integer simultaneousSeed;
    private final String client;
    private final boolean keepTorrentWithZeroLeechers;

    @JsonCreator
    public AppConfiguration(
            @JsonProperty(value = "minUploadRate", required = true) final Long minUploadRate,
            @JsonProperty(value = "maxUploadRate", required = true) final Long maxUploadRate,
            @JsonProperty(value = "simultaneousSeed", required = true) final Integer simultaneousSeed,
            @JsonProperty(value = "client", required = true) final String client,
            @JsonProperty(value = "keepTorrentWithZeroLeechers", required = true) final boolean keepTorrentWithZeroLeechers
    ) {
        this.minUploadRate = minUploadRate;
        this.maxUploadRate = maxUploadRate;
        this.simultaneousSeed = simultaneousSeed;
        this.client = client;
        this.keepTorrentWithZeroLeechers = keepTorrentWithZeroLeechers;

        validate();
    }

    public Long getMaxUploadRate() {
        return maxUploadRate;
    }

    public Long getMinUploadRate() {
        return minUploadRate;
    }

    public Integer getSimultaneousSeed() {
        return simultaneousSeed;
    }

    @JsonProperty("client")
    public String getClientFileName() {
        return client;
    }

    @JsonProperty("keepTorrentWithZeroLeechers")
    public boolean shouldKeepTorrentWithZeroLeechers() {
        return keepTorrentWithZeroLeechers;
    }

    private void validate() {
        if (java.util.Objects.isNull(minUploadRate)) {
            throw new AppConfigurationIntegrityException("minUploadRate must not be null");
        }
        if (minUploadRate < 0L) {
            throw new AppConfigurationIntegrityException("minUploadRate must be at least 0.");
        }
        if (java.util.Objects.isNull(maxUploadRate)) {
            throw new AppConfigurationIntegrityException("maxUploadRate must not be null");
        }
        if (maxUploadRate < 0L) {
            throw new AppConfigurationIntegrityException("maxUploadRate must greater or equal to 0.");
        }
        if (maxUploadRate < minUploadRate) {
            throw new AppConfigurationIntegrityException("maxUploadRate must be greater or equal to minUploadRate.");
        }
        if (java.util.Objects.isNull(simultaneousSeed)) {
            throw new AppConfigurationIntegrityException("simultaneousSeed must not be null");
        }
        if (simultaneousSeed < 1) {
            throw new AppConfigurationIntegrityException("simultaneousSeed must be greater than 0.");
        }
        if (StringUtils.isBlank(client)) {
            throw new AppConfigurationIntegrityException("client is required, no file name given.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AppConfiguration that = (AppConfiguration) o;
        return Objects.equal(minUploadRate, that.minUploadRate) &&
                Objects.equal(maxUploadRate, that.maxUploadRate) &&
                Objects.equal(simultaneousSeed, that.simultaneousSeed) &&
                Objects.equal(client, that.client) &&
                Objects.equal(keepTorrentWithZeroLeechers, that.keepTorrentWithZeroLeechers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(minUploadRate, maxUploadRate, simultaneousSeed, client, keepTorrentWithZeroLeechers);
    }
}
