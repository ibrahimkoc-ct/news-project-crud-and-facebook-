package com.ba.spring;

import com.ba.dto.Article;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/news")

public class NewsController {
    private static List<Article> allNews = new ArrayList<>();

@GetMapping("/list")
public List<Article> News() {


 return allNews;
}
    @GetMapping("/id/{id}")
    public Article getArticleById(@PathVariable Long id){
        Optional<Article> optionalArticle = allNews.stream().filter(news1 -> news1.getId()==id).findAny();
        if(!optionalArticle.isPresent()){
        System.out.println("sonuç bulunamadı");
        return null;
    }
    return optionalArticle.get();
    }
    @PostMapping("/add")
    public Article addNews(@RequestBody Article article){
    allNews.add(article);
        return article;

    }
    @PostMapping("/facebook")
    public void addFace(@RequestBody Article article) throws FacebookException {
        String result="Haber Başlığı: "+article.getTitle()+"\n Haber İçeriği: "+article.getDescription()+"\n Haber Yazarı: "+article.getAuthor()+".";
        Facebook facebook = new FacebookFactory().getInstance();
        String token="EAAF2f7hj0YcBAHqs2yVwphceGqrZAPBd5Cy3h9BFYiPnHZBvjxZCKe90kkFFjxgKxyHkg8w0V7ZAOkCUEzaeBt2Fr1eeOuHLCZADtQrr71i0kA5N6m0pqeBuYhuSkysNknzD1M8JNo5ZBTnJ3lkV770KqFcWBSgnNwc032AqzDCGhDYuti7FSTwoCFQGxztbgZD";
        facebook.setOAuthAppId(	"108438507753599", "bef99ef1281bec907f3590e3f65a4d34");
        facebook.setOAuthAccessToken(new AccessToken(token, null));
        try {
            facebook.postStatusMessage(result);
        } catch (FacebookException e) {
            e.printStackTrace();
        }

    }
    @PutMapping("/update/{id}")
    public Article updateNews(@PathVariable long id,@RequestBody Article article){
    Optional<Article> optionalArticle =allNews.stream().filter(news1-> news1.getId()==id).findAny();
    if(optionalArticle==null){
        System.out.println("girilen ID ile haber bulunamadı!");
        return null;
    }
    optionalArticle.get().setTitle(article.getTitle());
    optionalArticle.get().setAuthor(article.getAuthor());
    optionalArticle.get().setDescription(article.getDescription());
    optionalArticle.get().setUrlToImage(article.getUrlToImage());
    return article;
        }
    @DeleteMapping("/id/{id}")
    public List<Article> deleteNews(@PathVariable Long id) {
        allNews.removeIf(article -> article.getId() == id);
        return allNews;
    }
    static {
        Article article = new Article(1, "Milliyet.com.tr", "Son dakika - Fenerbahçe'de beklenen oldu! Erol Bulut yıldız ismi ilk 11'e alıyor... - Milliyet", "Sezon başında herkesin konuştuğu yıldız isim hem sakatlık etkileri hem de fiziksel toparlama nedeniyle henüz bekleneni veremedi. Erol Bulut, Konyaspor yenilgisinin ardından ilk 11’de değişikliğe giderek yıldız futbolcuyu sahaya sürecek", "https://i2.milimaj.com/i/milliyet/75/0x0/5fadd1db554281186c796364.jpg");
        Article article1 = new Article(2, "Yenicaggazetesi.com.tr", "Koronanın dehşet saçan yan etkisi. Covid-19'a yakalanan Doç. Dr. Aslıhan Candevir anlattı - YeniÇağ", "Korona virüs hastalığını yenin Çukurova Üniversitesi Tıp Fakültesi Enfeksiyon Hastalıkları ve Klinik Mikrobiyoloji Ana Bilim Dalı Öğretim Üyesi Doç. Dr. Aslıhan Candevir, \"Hastalık o kadar garip ki bağışıklık sistemine takla attırıyor. Bağışıklık sistemim bas…", "https://cdn.yenicaggazetesi.com.tr/news/454007.jpg");
        allNews.add(article);
        allNews.add(article1);
    }
}
