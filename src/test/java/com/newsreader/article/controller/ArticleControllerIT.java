package com.newsreader.article.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsreader.article.builder.ArticleTestBuilder;
import com.newsreader.article.entity.Article;
import com.newsreader.article.mapper.ArticleDTO2EntityMapper;
import com.newsreader.article.model.ArticleDTO;
import com.newsreader.article.repository.ArticleRepository;
import com.newsreader.article.rest.api.IdResponse;
import com.newsreader.article.rest.api.ListResponse;
import com.newsreader.article.rest.api.SimpleResponse;
import com.newsreader.article.util.DateUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleControllerIT {

    private static ArticleDTO testArticleDTO;
    private final String ARTICLES_ENDPOINT = "/articles";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleTestBuilder articleTestBuilder;
    @Autowired
    private ArticleDTO2EntityMapper articleDTO2EntityMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        testArticleDTO =
            buildTestArticleDTO("Deep Dive to OAuth2.0 and JWT (Part 4 JWT Use Case)", "test short description",
                "Up your Spring Security game with this tutorial!",
                DateUtils.convertStringToDate("2019-01-09"), Arrays.asList("Graham Hunter", "Dave Wilson"),
                Arrays.asList("Security", "Spring"));
    }

    @AfterEach
    public void tearDown() throws Exception {
        articleRepository.deleteAll();
    }

    @Test
    public void givenArticles_whenGetArticleById_thenStatus200()
        throws Exception {

        //save a test data directly in repository and later send a GET API request
        Article article = articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT + "/" + article.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get article dto from response object
        ArticleDTO responseArticleDTO = getArticleDTOFromResponse(result);

        //assert values got from API with test data
        Assert.assertEquals(testArticleDTO.getAuthors(), responseArticleDTO.getAuthors());
        Assert.assertEquals(testArticleDTO.getHeader(), responseArticleDTO.getHeader());
        Assert.assertEquals(testArticleDTO.getKeywords(), responseArticleDTO.getKeywords());
        Assert.assertEquals(testArticleDTO.getPublishDate(), responseArticleDTO.getPublishDate());
        Assert.assertEquals(testArticleDTO.getShortDescription(), responseArticleDTO.getShortDescription());
        Assert.assertEquals(testArticleDTO.getText(), responseArticleDTO.getText());
    }

    @Test
    public void givenArticles_whenGetArticleWhichNotExist_thenStatus200()
        throws Exception {
        mvc.perform(get(ARTICLES_ENDPOINT + "/" + 2)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Test
    @Transactional
    public void givenArticle_whenCreateAnArticle_thenStatus201()
        throws Exception {

        //perform post request for an article
        MvcResult result = mvc.perform(
            post(ARTICLES_ENDPOINT)
                .content(objectMapper.writeValueAsString(testArticleDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get id from API response and find in repository
        Long id = getIdFromResponse(result);
        Article article = articleRepository.findById(id).get();

        //assert values got from repository with test data
        Assert.assertEquals(testArticleDTO.getAuthors(), article.getAuthors());
        Assert.assertEquals(testArticleDTO.getHeader(), article.getHeader());
        Assert.assertEquals(testArticleDTO.getKeywords(), article.getKeywords());
        Assert.assertEquals(testArticleDTO.getPublishDate(), article.getPublishDate());
        Assert.assertEquals(testArticleDTO.getShortDescription(), article.getShortDescription());
        Assert.assertEquals(testArticleDTO.getText(), article.getText());
    }

    @Test
    @Transactional
    public void givenArticle_whenUpdateAnArticle_thenStatus200()
        throws Exception {

        //create a test article to be updated through API put request
        Article article = articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        testArticleDTO.setKeywords(Arrays.asList("Security, Spring, OAuth"));
        testArticleDTO.setPublishDate(DateUtils.convertStringToDate("2019-02-09"));

        //perform put request for an article
        mvc.perform(
            put(ARTICLES_ENDPOINT + "/" + article.getId())
                .content(objectMapper.writeValueAsString(testArticleDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //assert values got from repository with test data
        Article updatedArticle = articleRepository.findById(article.getId()).get();
        Assert.assertEquals(testArticleDTO.getAuthors(), updatedArticle.getAuthors());
        Assert.assertEquals(testArticleDTO.getHeader(), updatedArticle.getHeader());
        Assert.assertEquals(testArticleDTO.getKeywords(), updatedArticle.getKeywords());
        Assert.assertEquals(testArticleDTO.getPublishDate(), updatedArticle.getPublishDate());
        Assert.assertEquals(testArticleDTO.getShortDescription(), updatedArticle.getShortDescription());
        Assert.assertEquals(testArticleDTO.getText(), updatedArticle.getText());
    }

    @Test
    @Transactional
    public void givenArticle_whenDeleteAnArticle_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        Article article = articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //perform delete request for an article
        mvc.perform(
            delete(ARTICLES_ENDPOINT + "/" + article.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //assert for article should have been deleted
        Optional<Article> updatedArticle = articleRepository.findById(article.getId());
        Assert.assertFalse(updatedArticle.isPresent());
    }

    @Test
    @Transactional
    public void givenArticle_whenlistArticlesByAuthorCriteria_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //create bulk articles to test for pagination and searching
        testArticleDTO.setAuthors(Arrays.asList("John Williams"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        testArticleDTO.setAuthors(Arrays.asList("Graham Hunter", "Jeoffrey Archer"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //request params with multi value map
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("author", "Graham Hunter");

        //perform get request for list of articles based on criteria
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of article dto from response object
        List<ArticleDTO> responseArticleDTOs = getArticleDTOsFromListResponse(result);

        //assert for list of articles should match the criteria result count
        Assert.assertEquals(2, responseArticleDTOs.size());
    }

    @Test
    @Transactional
    public void givenArticle_whenlistArticlesByKeywordCriteria_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //create bulk articles to test for pagination and searching
        testArticleDTO.setKeywords(Arrays.asList("Security", "OAuth"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        testArticleDTO.setKeywords(Arrays.asList("Spring", "Auth"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //request params with multi value map
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("keyword", "OAuth");

        //perform get request for list of articles based on criteria
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of article dto from response object
        List<ArticleDTO> responseArticleDTOs = getArticleDTOsFromListResponse(result);

        //assert for list of articles should match the criteria result count
        Assert.assertEquals(1, responseArticleDTOs.size());
    }

    @Test
    @Transactional
    public void givenArticle_whenlistArticlesByPeriodCriteria_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //create bulk articles to test for pagination and searching
        testArticleDTO.setPublishDate(DateUtils.convertStringToDate("2019-01-10"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        testArticleDTO.setPublishDate(DateUtils.convertStringToDate("2019-01-11"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //request params with multi value map
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", "2019-01-09");
        requestParams.add("endDate", "2019-01-10");

        //perform get request for list of articles based on criteria
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of article dto from response object
        List<ArticleDTO> responseArticleDTOs = getArticleDTOsFromListResponse(result);

        //assert for list of articles should match the criteria result count
        Assert.assertEquals(2, responseArticleDTOs.size());
    }

    @Test
    @Transactional
    public void givenArticle_whenlistArticlesWithoutAnyCriteria_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //create bulk articles to test for pagination and searching
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //request params with multi value map (without any criteria)
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        //perform get request for list of articles based on criteria
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of article dto from response object
        List<ArticleDTO> responseArticleDTOs = getArticleDTOsFromListResponse(result);

        //assert for list of articles should match the criteria result count
        Assert.assertEquals(3, responseArticleDTOs.size());
    }

    @Test
    @Transactional
    public void givenArticle_whenlistArticlesWithAllCriteria_thenStatus200()
        throws Exception {

        //create a test article to be deleted through API delete request
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //create bulk articles to test for pagination and searching
        testArticleDTO.setPublishDate(DateUtils.convertStringToDate("2019-01-10"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        testArticleDTO.setPublishDate(DateUtils.convertStringToDate("2019-01-11"));
        articleRepository.save(articleDTO2EntityMapper.from(testArticleDTO));

        //request params with multi value map (without any criteria)
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("startDate", "2019-01-08");
        requestParams.add("endDate", "2019-01-09");
        requestParams.add("keyword", "Spring");
        requestParams.add("author", "Dave Wilson");

        //perform get request for list of articles based on criteria
        MvcResult result = mvc.perform(get(ARTICLES_ENDPOINT).params(requestParams)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

        //get list of article dto from response object
        List<ArticleDTO> responseArticleDTOs = getArticleDTOsFromListResponse(result);

        //assert for list of articles should match the criteria result count
        Assert.assertEquals(1, responseArticleDTOs.size());
    }

    private ArticleDTO getArticleDTOFromResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        SimpleResponse<ArticleDTO> simpleResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<SimpleResponse<ArticleDTO>>() {
            });

        return simpleResponse.getData().get(0);
    }

    private List<ArticleDTO> getArticleDTOsFromListResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        ListResponse<ArticleDTO> listResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<ListResponse<ArticleDTO>>() {
            });

        return listResponse.getData();
    }

    private Long getIdFromResponse(MvcResult result)
        throws com.fasterxml.jackson.core.JsonProcessingException, UnsupportedEncodingException {
        IdResponse idResponse = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            new TypeReference<IdResponse>() {
            });

        return idResponse.getData().get(0).getId().getId();
    }

    private ArticleDTO buildTestArticleDTO(String header, String shortDescription, String text, Date publishDate,
        List<String> authors, List<String> keywords) {
        ArticleDTO articleDTO = articleTestBuilder
            .withArticleDetails(header, shortDescription, text)
            .authoredBy(authors)
            .publishedOn(publishDate)
            .hasKeywords(keywords)
            .build();
        return articleDTO;
    }
}