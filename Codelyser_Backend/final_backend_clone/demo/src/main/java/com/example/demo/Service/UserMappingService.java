package com.example.demo.Service;

import java.util.HashMap;

public class UserMappingService {
    public static HashMap<Long,String> paths= new HashMap<Long,String>();
    public String filePath;
    public static HashMap<Long,Boolean[]> trackMap = new HashMap<>();
    public void insertInMap(Long userId,Long questionId)
    {
        if(trackMap.containsKey(questionId))
        {
           assignPath(questionId,userId);
        }
        else
        {
            Boolean[] track = {true,true,true};
            trackMap.put(questionId,track);
            assignPath(questionId,userId);
        }
        for(Long key:trackMap.keySet())
        {
            System.out.print(key+" : ");
            for(Boolean b:trackMap.get(key))
                System.out.print(b+",");
        }
        System.out.println();
        System.out.println(paths);
    }
    public void assignPath(Long questionId,Long userId)
    {
        Boolean[] track=trackMap.get(questionId);
        for(int i=0;i<track.length;i++)
        {
            if(track[i]==true)
            {
                filePath="C:\\Codelyser\\codelyser_user\\Solution_"+questionId+"_"+(i+1);
                paths.put(userId,filePath);
                track[i]=false;
                return;
            }
        }
        System.out.println("no clone is free....");
    }
    public String getFilePath(Long userId)
    {
        return  paths.containsKey(userId)?paths.get(userId):"file not found";
    }
}
