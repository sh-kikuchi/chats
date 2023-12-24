package com.project.chats.app.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.chats.entity.Post;
import com.project.chats.service.PostService;

@Controller /*コントローラのアノテーション*/
@RequestMapping("post") /*ドメイン以降のURLの指定*/
public class PostController {
	
	//プロパティ
	private final PostService postService;
	
	//デフォルトコンストラクタ(サービスを読み込む）
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	
   /**
   * リスト画面表示
   * postの全件リスト表示
   * @param　model 
   * @return　templateのhtmlファイル
   */
	@GetMapping
	public String index(Model model) {
		List<Post> list = postService.getAll();
		model.addAttribute("postList", list);
		model.addAttribute("title", "post_list" );
		
		return "post/index";
	}
	
	   /**
	   * リスト画面表示
	   * postの全件リスト表示
	   * @param　model 
	   * @return　templateのhtmlファイル
	   */
        @GetMapping("/list/{threadId}")
		public String showListByThread(
				@PathVariable(name = "threadId") int threadId,
				Model model) 
        {
        	List<Post> list = postService.getListByThread(threadId);
        	
    		model.addAttribute("postList", list);
    		model.addAttribute("title", "post_list_by_thread" );
    		
    		return "post/list";
        	
 
		}
	
   /**
   * フォーム画面表示
   * --postの全件リスト表示
   * @param　postForm, model ,complete
   * @return　templateのhtmlファイル
   */
	@GetMapping("/form")
	public String form(PostForm postForm,
			Model model,
			@ModelAttribute("complete") String complete
			) {
		model.addAttribute("title", "post");
		return "post/form";
		
	}

   /**
   * 確認画面からフォーム画面へ戻る
   * 
   * @param　postForm, model ,complete
   * @return　templateのhtmlファイル
   */	
	@PostMapping("/form")
	public String goBackToForm(PostForm postForm,Model model) {
		model.addAttribute("title", "post" );
		return  "post/form";
		
	}
	
    /**
     * 一件タスクデータを取得し、フォーム内に表示
     * @param threadForm
     * @param id
     * @param model
     * @return
     */
	@GetMapping("/form/{id}")
	public String getPostById (
			@PathVariable("id") int  id,        //パスのパラメータ
			PostForm postForm, //フォーム用オブジェクト
	        Model model                    //ビューとコントローラー間でデータを受け渡すためのインターフェース
	) {
		//【Service】Threadを取得(Optionalはnullを明示的に扱うための手段として導入され、nullを扱う場合のNullPointerExceptionを避けるのに役立つ）
		Optional<Post> postOpt = postService.getPost(id);
		
		//ThreadForm(フォーム用オブジェクト）
		Optional<PostForm> postFormOpt = postOpt.map(t -> makePostForm(t));
		
        //ThreadFormがnullでなければ中身を取り出し
        if(postFormOpt.isPresent()) {
        	postForm = postFormOpt.get();
        }
        
		model.addAttribute("postForm", postFormOpt);
		model.addAttribute("title", "post" );
		return  "post/form";
	}
	
    /**
     *  【更新用】PostのデータをPostFormに入れて返す
     * @param task
     * @return
     */
    private PostForm makePostForm(Post post) {

    	PostForm postForm = new PostForm();

    	postForm.setId(post.getId());
        postForm.setUser_id(post.getUser_id());
    	postForm.setThread_id(post.getThread_id());
       	postForm.setTitle(post.getTitle());
    	postForm.setDescription(post.getDescription());
    	postForm.setNewPost(false);
    	
        return postForm;
    }
   /**
   * 確認画面表示機能
   * --ヴァリデーション機能あり
   * @param　postForm, BindingResult, model 
   * @return　templateのhtmlファイル
   */	
	@PostMapping("/confirm")
	public String confirm(@Validated PostForm postForm, BindingResult result, Model model){
		
		if(result.hasErrors()) {
			model.addAttribute("title", "post" );
			return "post/form";
		}
		model.addAttribute("title", "confirm");
		return "post/confirm";
	}
	
	
   /**
   *登録機能
   * --ヴァリデーション機能あり
   * @param　postForm, BindingResult, model, redirectAttributes 
   * @return　templateのhtmlファイル（リダイレクト）
   */	
	@PostMapping("/complete")
	public String complete(@Validated PostForm postForm,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		//バリデーションチェック
		if(result.hasErrors()) {
			model.addAttribute("title", "thread" );
			return "post/form";
		}
		
		//エンティティのインスタンスに詰め替える
		Post post = new Post();
		post.setThread_id(postForm.getThread_id());
		post.setUser_id(postForm.getUser_id());
		post.setTitle(postForm.getTitle());
		post.setDescription(postForm.getDescription());
		post.setCreated_at(LocalDateTime.now());
		post.setUpdated_at(LocalDateTime.now());
		
		
	    // 登録処理または更新処理
	    if (postForm.getId() > 0) {
	        // IDが0より大きい場合はUPDATE
	        post.setId(postForm.getId());
	        postService.update(post);
	    } else {
	        // IDが0以下の場合はCREATE
	    	postService.save(post);
	    }
		
		
		//セッションに格納（フラッシュスコープ）
		redirectAttributes.addFlashAttribute("complete", "registered");
		return "redirect:/post/"+ postForm.getThread_id(); //URLを指定 
		
	}
	
    /**
     * タスクidを取得し、一件のデータ更新
     * @param postForm
     * @param result
     * @param model
     * @param redirectAttributes
     * @return
     */
	@PostMapping("/update")
	public String update(
			@Validated @ModelAttribute PostForm postForm,
			BindingResult result,
			@RequestParam("postId") int postId,
			Model model,
			RedirectAttributes redirectAttributes){
		
		//PostFormのデータをPostに格納する
		Post post = makePost(postForm, postId);
		
		if (!result.hasErrors()) {
			//更新処理、フラッシュスコープ、リダイレクト
			postService.update(post);
			redirectAttributes.addFlashAttribute("complete", "変更が完了しました");
			return "redirect:/post/" + postId;
		} else {
			model.addAttribute("postForm", postForm);
			model.addAttribute("title", "スレッド一覧");
			return "post/index";
		}
	}
	
    /**
     * タスクidを取得し、一件のデータ削除
     * @param id
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String delete(
    	@RequestParam("id") int id,
    	@RequestParam("thread_id") int thread_id,
    	Model model) {

    	//タスクを一件削除しリダイレクト
    	postService.delete(id);

    	return "redirect:/post/list/"+ thread_id;
    }

   /**
     * PostFormのデータをPostに入れて返す
     * @param postForm
     * @param postId 新規登録の場合は0を指定
     * @return
     */
	private Post makePost(PostForm postForm, int postId) {
	  	Post post = new Post();
        if(postId != 0) {
        	post.setId(postId);
        }
        post.setUser_id(1);
        post.setThread_id(1);
        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());

        return post;
	}
	
	
	

}
