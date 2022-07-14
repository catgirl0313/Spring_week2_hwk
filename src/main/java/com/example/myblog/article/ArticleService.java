package com.example.myblog.article;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(ArticleSaveDto dto) {
        articleRepository.save(dto.toEntity()); //entity 에서 controller에서 받은 디디오를 저장..?>

    }

    public List<ArticlesDto> articles() {
        return articleRepository.findAll()
                .stream()
                .map(ArticlesDto::new) //dto -> {return new articlesdto(dto);} map:class  fh qkRnjwnj.
                .collect(Collectors.toList());//list라는 박스에 담겠다.
    }

    public ArticlesDto findOne(Long articleId) {
        Articles entity = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));

        return new ArticlesDto(entity);
    }
}
