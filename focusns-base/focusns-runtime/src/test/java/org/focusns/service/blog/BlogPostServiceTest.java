package org.focusns.service.blog;

import java.util.Date;
import junit.framework.Assert;
import org.focusns.model.blog.BlogPost;
import org.focusns.model.blog.BlogTag;
import org.focusns.model.common.Page;
import org.focusns.model.core.Project;
import org.focusns.model.core.User;
import org.focusns.service.AbstractServiceTest;
import org.focusns.service.core.ProjectService;
import org.focusns.service.core.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class BlogPostServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BlogTagService tagService;
    @Autowired
    private BlogPostService postService;
    
    @Test
    public void testCreateBlogPost() {
        Project project = projectService.getProject("focusns");
        Assert.assertNotNull(project);
        //
        BlogTag blogTag = new BlogTag();
        blogTag.setName("未分类");
        blogTag.setProjectId(project.getId());
        //
        tagService.createBlogTag(blogTag);
        //
        User user = userService.getUser("admin");
        Assert.assertNotNull(user);
        //
        Date now = new Date();
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Blog Title");
        blogPost.setContent("Blog Content");
        blogPost.setCreateAt(now);
        blogPost.setModifyAt(now);
        blogPost.setTagId(blogTag.getId());
        blogPost.setCreateById(user.getId());
        //
        postService.createBlogPost(blogPost);
    }
    
    @Test
    public void testRemoveBlogPost() {
        Project project = projectService.getProject("focusns");
        Assert.assertNotNull(project);
        //
        Page<BlogPost> page = new Page<BlogPost>(10);
        page = postService.fetchPageByProjectId(page, project.getId());
        for(BlogPost post : page.getResults()) {
            postService.removeBlogPost(post);
        }
    }
    
}