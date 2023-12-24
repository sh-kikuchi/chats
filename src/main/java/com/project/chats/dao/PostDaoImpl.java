package com.project.chats.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.chats.entity.Post;

@Repository
public class PostDaoImpl implements PostDao {
	
	//DB操作のプロパティ
	private final JdbcTemplate jdbcTemplate;
	
	//クラス名と同じのデフォルトコンストラクタを作成する
	@Autowired
	public PostDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertPost(Post post) {
		jdbcTemplate.update("INSERT INTO posts(thread_id, user_id, title, description, created_at, updated_at) VALUES(?,?,?,?,?,?);",
				post.getThread_id(), post.getUser_id(), post.getTitle(), post.getDescription(), post.getCreated_at(), post.getUpdated_at());
	}
	
	@Override
	public void updatePost(Post post) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("UPDATE posts  SET  title=?, description=?,  updated_at=?  WHERE id = ?;",
			post.getTitle(), post.getDescription(), post.getUpdated_at() , post.getId());
	}

	@Override
	public int deletePost(int id) {
		return jdbcTemplate.update("DELETE FROM posts WHERE id = ?", id);
	}

	@Override
	public List<Post> getAll() {
		//SQL発行
		String sql = "SELECT id, thread_id, user_id, title, description, created_at, updated_at FROM posts";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		//SQLの実行結果をエンティティのインスタンスに詰め替えている
		List<Post> list = new ArrayList<Post>();

System.out.println(resultList);
		for(Map<String, Object> result : resultList) {
			Post post = new Post();
			post.setId((int)result.get("id"));
			post.setThread_id((int)result.get("thread_id"));
			post.setUser_id((int)result.get("user_id"));
			post.setTitle((String)result.get("title"));
			post.setDescription((String)result.get("description"));
			post.setCreated_at(((Timestamp)result.get("created_at")).toLocalDateTime());
			post.setUpdated_at(((Timestamp)result.get("updated_at")).toLocalDateTime());
			//戻り値用のリストに格納
			list.add(post);
		}
		
		return list;
	}
	
	@Override
	public List<Post> getListByThread(int id){
		String sql = "SELECT id, thread_id, user_id, title, description, created_at, updated_at "
				+ "FROM posts "
				+ "WHERE thread_id=?";
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, id);
		//SQLの実行結果をエンティティのインスタンスに詰め替えている
		List<Post> list = new ArrayList<Post>();
		
		for(Map<String, Object> result : resultList) {
			Post post = new Post();
			post.setId((int)result.get("id"));
			post.setThread_id((int)result.get("thread_id"));
			post.setUser_id((int)result.get("user_id"));
			post.setTitle((String)result.get("title"));
			post.setDescription((String)result.get("description"));
			post.setCreated_at(((Timestamp)result.get("created_at")).toLocalDateTime());
			post.setUpdated_at(((Timestamp)result.get("updated_at")).toLocalDateTime());
			//戻り値用のリストに格納
			list.add(post);
		}
		return list;
	}
	

	
	@Override
	public Optional<Post> findById(int id) {

		String sql = "SELECT id, thread_id, user_id, title, description, created_at, updated_at FROM posts WHERE id = ?";

		//タスク一覧をMapのListで取得
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id); //SQLを実行する

		//テーブルのデータをTaskにまとめる
		Post post = new Post();
		post.setId((int)result.get("id"));
		post.setThread_id((int)result.get("thread_id"));
		post.setUser_id((int)result.get("user_id"));
		post.setTitle((String)result.get("title"));
		post.setDescription((String)result.get("description"));

		//optional(その値がnullかもしれないことを表現する。NullPointerExceptionを防ぐ。
		Optional<Post> postOpt = Optional.ofNullable(post);
		
		return postOpt;
	}
	
	
	

}
