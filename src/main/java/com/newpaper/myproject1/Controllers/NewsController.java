package com.newpaper.myproject1.Controllers;

import com.newpaper.myproject1.Models.CommentsNews;
import com.newpaper.myproject1.Models.NewsPost;
import com.newpaper.myproject1.Repo.CommentsNewsRepository;
import com.newpaper.myproject1.Repo.NewsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@Import(WebConfig.class)
@Controller
public class NewsController {
//    @Value("${uploadPath}")
//    private String uploadPath;

    Path path = Paths.get("static/images/");
//    Path path = Paths.get("c:/Users/Volodymyr/IdeaProjects/my-project-1/target/classes/static/images/");
    @Autowired
    private NewsPostRepository newsRepository;
    @Autowired
    private CommentsNewsRepository commentsRepository;
    @GetMapping("/news")
    public String News (Model model) {
        Iterable<NewsPost> IColectionNews = newsRepository.findAll();
        ArrayList<NewsPost> arrayListNews = new ArrayList();
        for (NewsPost item: IColectionNews){
            arrayListNews.add(item);
        }
        Collections.reverse(arrayListNews);
        model.addAttribute("news", arrayListNews);

        Iterable<CommentsNews> IColectionComments = commentsRepository.findAll();
        model.addAttribute("comments", IColectionComments);
        return "news";
    }


    @GetMapping("/news/newsadmin")
    public String NewsAdmin (Model model) {
        Iterable<NewsPost> IColectionNews = newsRepository.findAll();
        ArrayList<NewsPost> arrayListNews = new ArrayList();
        for (NewsPost item: IColectionNews){
            arrayListNews.add(item);
        }
        Collections.reverse(arrayListNews);
        model.addAttribute("news", arrayListNews);
        return "newsadmin";
    }

    @PostMapping("/news/newsadmin/{id}")
    public String NewsAdminById (@PathVariable(value="id") long id, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            model.addAttribute("newsPost", newsPost);
            return "shownewadmin";
        }
        return "redirect:/news";
    }

//    @GetMapping("/")
//    public String NewsHome (Model model) {
//        Iterable<NewsPost> IColectionNews = newsRepository.findAll();
//        ArrayList<NewsPost> arrayListNews = new ArrayList();
//        for (NewsPost item: IColectionNews){
//            arrayListNews.add(item);
//        }
//        Collections.reverse(arrayListNews);
//        model.addAttribute("home", arrayListNews);
//        return "home";
//    }
    @GetMapping("/news/addnews")
    public String AddNews (Model model) {
        return "addnews";
    }

//    , RedirectAttributes redirectAttributes
    @PostMapping("/news/addnews")
    public String AddNewsPost (@RequestParam String title, @RequestParam String text, @RequestParam("image") MultipartFile file, Model model) throws IOException {


        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

//        String image = file.getOriginalFilename();
        String uuidFileName = "";
        if (file.getOriginalFilename().equals("")){
            uuidFileName = "NewsPhoto.png";
        } else uuidFileName = UUID.randomUUID().toString();

//        File imageFile = new File(path + "/" + uuidFileName);
//        file.transferTo(imageFile);

        FileUploadUtil.saveFile(path, uuidFileName, file);




        NewsPost newsPost = new NewsPost(
                title,
                text,
                uuidFileName
        );
        newsRepository.save(newsPost);
        return "redirect:/news";
    }
    @GetMapping("/news/{id}")
    public String NewsById (@PathVariable(value="id") long id, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            model.addAttribute("newsPost", newsPost);

            Iterable<CommentsNews> commentsNewsIterable = commentsRepository.findByNewsPost_id(id);
//            Iterable<CommentsNews> commentsNews = commentsRepository.findAll();
            ArrayList<CommentsNews> arrayListComments = new ArrayList();
            for (CommentsNews item: commentsNewsIterable){
                arrayListComments.add(item);
            }
            model.addAttribute("newsComments", arrayListComments);
            return "shownew";
        }
        return "redirect:/news";
    }

    @PostMapping("/news/{id}")
//    public String NewsByIdPost (@PathVariable(value="id") long id, @RequestParam String textcomment, Model model) {
    public String NewsByIdPost (@PathVariable(value="id") long id, @RequestParam String namecomment, @RequestParam String textcomment, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            model.addAttribute("newsPost", newsPost);

            CommentsNews commentsNews = new CommentsNews(
                    namecomment,
                    textcomment,
                    newsRepository.findById(id).get()
            );
            if (commentsRepository!=null) {
            }
            commentsRepository.save(commentsNews);

            Iterable<CommentsNews> commentsNewsIterable = commentsRepository.findByNewsPost_id(id);
//            Iterable<CommentsNews> commentsNews = commentsRepository.findAll();
            ArrayList<CommentsNews> arrayListComments = new ArrayList();
            for (CommentsNews item: commentsNewsIterable){
                arrayListComments.add(item);
            }
            model.addAttribute("newsComments", arrayListComments);

            return "shownew";
        }
        return "redirect:/news";
    }

//    @Controller
//    public class MyController {
//
//        @RequestMapping(value = "/openModal/{id}", method = RequestMethod.GET)
//        public String openModal(@PathVariable("id") String id, ModelMap model) {
//            model.addAttribute("id", id);
//            return "myModalView";
//        }
//    }

    @GetMapping("/news/{id}/update")
    public String NewsUpdate (@PathVariable(value="id") long id, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            model.addAttribute("newsPost", newsPost);
            return "updatenew";
        }
        return "redirect:/news";
    }
    @PostMapping("/news/{id}/update")
    public String NewsUpdatePost (@PathVariable(value="id") long id, @RequestParam String title, @RequestParam String text, @RequestParam("image") MultipartFile file, Model model) throws IOException {
//        String image = file.getOriginalFilename();
//        if(file.getOriginalFilename() == "" )
//        {
//            return "redirect:/news";
//        }
//    @PostMapping("/news/{id}/update")
//    public String NewsUpdatePost (@PathVariable(value="id") long id, @RequestParam String title, @RequestParam String text, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            if(file.getOriginalFilename().equals("") && newsPost.getTitle().equals(title) && newsPost.getText().equals(text))
            {
                return "redirect:/news/newsadmin";
            }
            if(!file.getOriginalFilename().equals("") )
            {
                String uuidFileName = UUID.randomUUID().toString();
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
//                File imageFile = new File(path + "/" + uuidFileName);
//                file.transferTo(imageFile);
                FileUploadUtil.saveFile(path, uuidFileName, file);
                newsPost.setImage(uuidFileName);
                newsRepository.save(newsPost);
            }
            if(!newsPost.getTitle().equals(title))
            {
                newsPost.setTitle(title);
            }
            if(!newsPost.getText().equals(text))
            {
                newsPost.setText(text);
            }
            newsPost.setDate();
            newsRepository.save(newsPost);
        }
        return "redirect:/news/newsadmin";
    }
    @GetMapping("/news/{id}/delete")
    public String NewsDelete (@PathVariable(value="id") long id, Model model) {
        if(newsRepository.existsById(id)){
            NewsPost newsPost = newsRepository.findById(id).get();
            newsRepository.delete(newsPost);
        }
        return "redirect:/news/newsadmin";
    }
}
