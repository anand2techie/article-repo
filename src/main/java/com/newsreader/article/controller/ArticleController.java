package com.newsreader.article.controller;

import com.newsreader.article.model.ArticleDTO;
import com.newsreader.article.rest.api.IdResponse;
import com.newsreader.article.rest.api.ListResponse;
import com.newsreader.article.rest.api.PageDTO;
import com.newsreader.article.rest.api.SimpleResponse;
import com.newsreader.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponse<ArticleDTO> getArticle(@PathVariable Long id) {
        return new SimpleResponse(articleService.getArticleById(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        return new IdResponse(articleService.createArticle(articleDTO));
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO articleDTO) {
        articleService.updateArticleById(id, articleDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
    }

    /*
    Single API to list all articles for a given author, period and specific keyword.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<ArticleDTO> listArticles(@RequestParam(defaultValue = "0", required = false) Integer page,
        @RequestParam(defaultValue = "10", required = false) Integer pageSize,
        @RequestParam Map<String, String> viewParams) {

        List<ArticleDTO> articleDTOList = articleService.listArticles(page, pageSize, viewParams);
        return new ListResponse<>(new PageDTO<>(page, pageSize, articleDTOList.size(), articleDTOList));
    }
}
