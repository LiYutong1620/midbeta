package com.example.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻资讯对象 t_news
 *
 * @author tong
 * @date 2026-05-08
 */
@Schema(description = "新闻资讯对象")
public class News extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Schema(description = "主键ID")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** 标题 */
    @Schema(description = "新闻标题", required = true)
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String title;

    /** 新闻摘要，用于列表展示 */
    @Schema(description = "新闻摘要")
    @Excel(name = "新闻摘要，用于列表展示")
    private String summary;

    /** 分类ID */
    @Schema(description = "所属分类ID")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long categoryId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String coverUrl;

    /** $column.columnComment */
    private String content;

    /** 0:草稿, 1:已发布 */
    @Excel(name = "0:草稿, 1:已发布")
    private Integer publishStatus;

    /** 0:下架, 1:上架 */
    @Excel(name = "0:下架, 1:上架")
    private Integer shelfStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long publishUserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date publishTime;

    /** $column.columnComment */
    private Long version;

    /** $column.columnComment */
    private Integer isDeleted;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createdAt;

    /** $column.columnComment */
    private Date updatedAt;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCoverUrl(String coverUrl)
    {
        this.coverUrl = coverUrl;
    }

    public String getCoverUrl()
    {
        return coverUrl;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setPublishStatus(Integer publishStatus)
    {
        this.publishStatus = publishStatus;
    }

    public Integer getPublishStatus()
    {
        return publishStatus;
    }

    public void setShelfStatus(Integer shelfStatus)
    {
        this.shelfStatus = shelfStatus;
    }

    public Integer getShelfStatus()
    {
        return shelfStatus;
    }

    public void setPublishUserId(Long publishUserId)
    {
        this.publishUserId = publishUserId;
    }

    public Long getPublishUserId()
    {
        return publishUserId;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }

    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }


    @Excel(name = "分类名称")
    private String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("summary", getSummary())
                .append("categoryId", getCategoryId())
                .append("coverUrl", getCoverUrl())
                .append("content", getContent())
                .append("publishStatus", getPublishStatus())
                .append("shelfStatus", getShelfStatus())
                .append("publishUserId", getPublishUserId())
                .append("publishTime", getPublishTime())
                .append("version", getVersion())
                .append("isDeleted", getIsDeleted())
                .append("createdAt", getCreatedAt())
                .append("updatedAt", getUpdatedAt())
                .toString();
    }
}
