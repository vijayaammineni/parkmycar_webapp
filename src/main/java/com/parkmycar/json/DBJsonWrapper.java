package com.parkmycar.json;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.parkmycar.Utils;
import com.parkmycar.db.DBOperations;
import com.parkmycar.utils.MD5Hash;

public class DBJsonWrapper {
		public static void getAllNotes (String currentHash, DBOperations dbOperations, NoteItJson noteItJson, String userId) throws NumberFormatException, SQLException {
			ArrayList<Note> notes = new ArrayList<Note>();
			ResultSet rs = dbOperations.getAllNotes(Long.valueOf(userId));
			StringBuffer sb = new StringBuffer();
			while(rs.next())
			{
				Note note = new Note();
				String note_title = rs.getString("title");
				Blob blob = rs.getBlob("content");
				byte[] contentData = blob.getBytes(1,(int)blob.length());
				Long noteId = rs.getLong("note_id");
				Date dateCreated = rs.getDate("date_created");
				Date dateModified = rs.getDate("date_modified");
				String tags = Utils.getTagsAsString(dbOperations.getTagsForNote(noteId));
				note.setId(String.valueOf(noteId));
				note.setTitle (note_title);
				note.setTags(tags);
				note.setContent(new String(contentData));
				note.setDateCreated(dateCreated.toString());
				note.setDateLastModified(dateModified.toString());
				sb.append(new String(contentData) + tags);
				notes.add(note);
			}
			String newHash = MD5Hash.md5(sb.toString());
			if (newHash != null && !newHash.equals(currentHash)) {
				noteItJson.setChanged("true");
				noteItJson.setNotes(notes);
				noteItJson.setHash(newHash);
			} else {
				noteItJson.setChanged("false");
			}
			noteItJson.setResult("success");
			
		}
}
