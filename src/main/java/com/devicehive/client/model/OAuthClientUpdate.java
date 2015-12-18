package com.devicehive.client.model;

import com.devicehive.client.StringUtil;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



@ApiModel(description = "")
public class OAuthClientUpdate   {
  
  @SerializedName("id")
  private Long id = null;
  
  @SerializedName("name")
  private String name = null;
  
  @SerializedName("domain")
  private String domain = null;
  
  @SerializedName("subnet")
  private String subnet = null;
  
  @SerializedName("redirectUri")
  private String redirectUri = null;
  
  @SerializedName("oauthId")
  private String oauthId = null;
  

  
  /**
   **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getDomain() {
    return domain;
  }
  public void setDomain(String domain) {
    this.domain = domain;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getSubnet() {
    return subnet;
  }
  public void setSubnet(String subnet) {
    this.subnet = subnet;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getRedirectUri() {
    return redirectUri;
  }
  public void setRedirectUri(String redirectUri) {
    this.redirectUri = redirectUri;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  public String getOauthId() {
    return oauthId;
  }
  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OAuthClientUpdate {\n");
    
    sb.append("    id: ").append(StringUtil.toIndentedString(id)).append("\n");
    sb.append("    name: ").append(StringUtil.toIndentedString(name)).append("\n");
    sb.append("    domain: ").append(StringUtil.toIndentedString(domain)).append("\n");
    sb.append("    subnet: ").append(StringUtil.toIndentedString(subnet)).append("\n");
    sb.append("    redirectUri: ").append(StringUtil.toIndentedString(redirectUri)).append("\n");
    sb.append("    oauthId: ").append(StringUtil.toIndentedString(oauthId)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}