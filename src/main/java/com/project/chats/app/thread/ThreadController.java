package com.project.chats.app.thread;

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

import com.project.chats.entity.Thread;
import com.project.chats.service.ThreadService;


@Controller /*コントローラのアノテーション*/
@RequestMapping("/thread")  /*ドメイン以降のURLの指定*/
public class ThreadController {
	
	private final ThreadService threadService;
	
	
	//デフォルトコンストラクタ
	@Autowired
	public ThreadController(ThreadService threadService) {
		 this.threadService = threadService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		List<Thread> list = threadService.getAll();
		model.addAttribute("threadList", list);
		model.addAttribute("title", "thread_list" );
		
		return "thread/index";
	}
	
	@GetMapping("/form")
	public String form(ThreadForm ｔhreadForm,
			Model model,
			@ModelAttribute("complete") String complete /*フラッシュスコープの値を受け取る*/
	) {
		model.addAttribute("title", "thread" );
		return  "thread/form";
		
	}
	
	@PostMapping("/form")
	public String goBackToForm(ThreadForm ｔhreadForm, Model model) {
		model.addAttribute("title", "thread" );
		return  "thread/form";
		
	}
	
    /**
     * 一件タスクデータを取得し、フォーム内に表示
     * @param threadForm
     * @param id
     * @param model
     * @return
     */
	@GetMapping("/form/{id}")
	public String getThreadById (
			@PathVariable("id") int  id,        //パスのパラメータ
			ThreadForm threadForm, //フォーム用オブジェクト
	        Model model                    //ビューとコントローラー間でデータを受け渡すためのインターフェース
	) {
		//【Service】Threadを取得(Optionalはnullを明示的に扱うための手段として導入され、nullを扱う場合のNullPointerExceptionを避けるのに役立つ）
		Optional<Thread> threadOpt = threadService.getThread(id);
		
		//ThreadForm(フォーム用オブジェクト）
		Optional<ThreadForm> threadFormOpt = threadOpt.map(t -> makeThreadForm(t));
		
        //ThreadFormがnullでなければ中身を取り出し
        if(threadFormOpt.isPresent()) {
        	threadForm = threadFormOpt.get();
        }
        
		model.addAttribute("threadForm", threadFormOpt);
		model.addAttribute("title", "thread" );
		return  "thread/form";
	}
	
    /**
     *  【更新用】ThreadのデータをThreadFormに入れて返す
     * @param task
     * @return
     */
    private ThreadForm makeThreadForm(Thread thread) {

    	ThreadForm threadForm = new ThreadForm();

    	threadForm.setId(thread.getId());
    	threadForm.setUser_id(thread.getUser_id());
       	threadForm.setName(thread.getName());
    	threadForm.setExplain(thread.getExplain());
    	threadForm.setNewThread(false);
    	
        return threadForm;
    }
	
	
    @PostMapping("/confirm")
	public String confrim(@Validated ThreadForm ｔhreadForm, BindingResult result, Model model){
		
		if(result.hasErrors()) {
			model.addAttribute("title", "thread" );
			return "thread/form";
		}
		model.addAttribute("title", "confirm");
		return "thread/confirm";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated ThreadForm ｔhreadForm,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "thread" );
			return "thread/form";
		}
		
		
		//エンティティのインスタンスに詰め替える
		Thread thread = new Thread();
		thread.setUser_id(ｔhreadForm.getUser_id());
		thread.setName(ｔhreadForm.getName());
		thread.setExplain(ｔhreadForm.getExplain());
		thread.setCreated_at(LocalDateTime.now());
		thread.setUpdated_at(LocalDateTime.now());
		
	    // 登録処理または更新処理
	    if (ｔhreadForm.getId() > 0) {
	        // IDが0より大きい場合はUPDATE
	        thread.setId(ｔhreadForm.getId());
	        threadService.update(thread);
	    } else {
	        // IDが0以下の場合はCREATE
	        threadService.save(thread);
	    }
		
		//セッションに格納（フラッシュスコープ）
		redirectAttributes.addFlashAttribute("complete", "registered");
		return "redirect:/thread/"; //URLを指定 
		
	}

    /**
     * スレッドidを取得し、一件のデータ更新
     * @param taskForm
     * @param result
     * @param model
     * @param redirectAttributes
     * @return
     */
	@PostMapping("/update")
	public String update(
			@Validated @ModelAttribute ThreadForm threadForm,
			BindingResult result,
			@RequestParam("threadId") int threadId,
			Model model,
			RedirectAttributes redirectAttributes){
		
		//ThreadFormのデータをThreadに格納する
		Thread thread = makeThread(threadForm, threadId);
		
		if (!result.hasErrors()) {
			//更新処理、フラッシュスコープ、リダイレクト
			threadService.update(thread);
			redirectAttributes.addFlashAttribute("complete", "変更が完了しました");
			return "redirect:/thread/" + threadId;
		} else {
			model.addAttribute("threadForm", threadForm);
			model.addAttribute("title", "スレッド一覧");
			return "thread/index";
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
    	Model model) {

    	//タスクを一件削除しリダイレクト
    	threadService.delete(id);

    	return "redirect:/thread/";
    }

   /**
     * ThreadFormのデータをThreadに入れて返す
     * @param threadForm
     * @param threadId 新規登録の場合は0を指定
     * @return
     */
	private Thread makeThread(ThreadForm threadForm, int threadId) {
	  	Thread thread = new Thread();
        if(threadId != 0) {
        	thread.setId(threadId);
        }
        thread.setUser_id(1);
        thread.setName(threadForm.getName());
        thread.setExplain(threadForm.getExplain());

        return thread;
	}
	
}
