package com.project.chats.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.chats.entity.Thread;

@Repository
public class ThreadDaoImpl implements ThreadDao {
	
	//DB操作のプロパティ
	private final JdbcTemplate jdbcTemplate;
	
	//クラス名と同じのデフォルトコンストラクタを作成する
	@Autowired
	public ThreadDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public void insertThread(Thread thread) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("INSERT INTO threads(user_id, name, explain, created_at, updated_at) VALUES(?,?,?,?,?);",
				thread.getUser_id(), thread.getName(), thread.getExplain(), thread.getCreated_at(), thread.getUpdated_at());
	}
	
	@Override
	public void updateThread(Thread thread) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("UPDATE threads  SET  user_id = ?,  name=?, explain=?,  updated_at=?  WHERE id = ?;",
				thread.getUser_id(), thread.getName(), thread.getExplain(), thread.getUpdated_at() , thread.getId());
	}

	@Override
	public int deleteThread(int id) {
		return jdbcTemplate.update("DELETE FROM threads WHERE id = ?", id);
	}


	@Override
	public List<Thread> getAll() {
		String sql = "SELECT id, user_id, name, explain, created_at, updated_at FROM threads";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		
		//SQLの実行結果をインスタンスに詰め替えている
		List<Thread> list = new ArrayList<Thread>();
		for(Map<String, Object> result : resultList) {
			Thread thread = new Thread();
			thread.setId((int)result.get("id"));
			thread.setUser_id((int)result.get("user_id"));
			thread.setName((String)result.get("name"));
			thread.setExplain((String)result.get("explain"));
			thread.setCreated_at(((Timestamp)result.get("created_at")).toLocalDateTime());
			thread.setUpdated_at(((Timestamp)result.get("updated_at")).toLocalDateTime());
			//戻り値用のリストに格納
			list.add(thread);
		}
		
		return list;
	}
	
	
	@Override
	public Optional<Thread> findById(int id) {

		String sql = "SELECT id, user_id, name, explain, created_at, updated_at FROM threads WHERE id = ?";

		//タスク一覧をMapのListで取得
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, id); //SQLを実行する

		//テーブルのデータをTaskにまとめる
		Thread thread = new Thread();
		thread.setId((int)result.get("id"));
		thread.setUser_id((int)result.get("user_id"));
		thread.setName((String)result.get("name"));
		thread.setExplain((String)result.get("explain"));

		//optional(その値がnullかもしれないことを表現する。NullPointerExceptionを防ぐ。
		Optional<Thread> threadOpt = Optional.ofNullable(thread);
		
		return threadOpt;
	}
	

}
