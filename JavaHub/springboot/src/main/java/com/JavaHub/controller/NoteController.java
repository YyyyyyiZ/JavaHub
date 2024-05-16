package com.JavaHub.controller;

import com.JavaHub.entity.Note;
import com.JavaHub.mapper.NoteMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/note")
@CrossOrigin(origins = "*")
public class NoteController {

    @Resource
    private NoteMapper noteMapper;

    @PostMapping("/updateNote")
    public void updateNote(@RequestBody Note note){
        String userid = note.getUserid();
        String secid = note.getSecid();
        String userNote = note.getRemark();
        String theNote = noteMapper.readNote(userid,secid);
        if (theNote==null){
            System.out.println("insertNote");
            noteMapper.insert(note);
        }else {
            noteMapper.updateNote(userid,secid,userNote);
            System.out.println("updateNote");
        }
    }

    @PostMapping("/readNote")
    public String readNote(@RequestBody Note note){
        String userid = note.getUserid();
        String secid = note.getSecid();
        return noteMapper.readNote(userid,secid);
    }
}
