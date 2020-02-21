package com.example.lol_ban_pick_manager;

import java.io.Serializable;
import java.util.ArrayList;

public class Champion implements Serializable {
    String name;
    int image;
    boolean isChampion = true;


    public Champion(int position){
        if(position == 0){
            name = "아무 챔피언";
            image = R.drawable.randomchampion;
        }else if(position == 1){
            name = "탑 챔피언";
            image = R.drawable.position_top;
        }else if(position == 2){
            name = "정글 챔피언";
            image = R.drawable.position_jg;
        }else if(position == 3){
            name = "미드 챔피언";
            image = R.drawable.position_mid;
        }else if(position == 4){
            name = "바텀 챔피언";
            image = R.drawable.position_bot;
        }else if(position == 5){
            name = "서포터 챔피언";
            image = R.drawable.position_sup;
        }
        isChampion = false;
    }
    public Champion(String name, int image){
        this.name = name;
        this.image = image;
    }

    public static Champion makePlus(){
        Champion champion = new Champion("추가", R.drawable.plus);
        champion.isChampion = false;
        return champion;
    }

    public static int getChampionIndex(String name){
        int i;
        for(i = 0; i < ApplicationClass.champions.size(); i++){
            Champion champion = ApplicationClass.champions.get(i);
            if(champion.name == name){
                System.out.println(name);
                return i;
            }
        }
        System.out.println("no champ");
        return -1;
    }

    public static int getChampionImage(int pos){
        if(pos == -1){
            return -1;
        }
        Champion champion = ApplicationClass.champions.get(pos);
        return champion.image;
    }
    public static Champion getChampion(int pos){
        Champion champion = ApplicationClass.champions.get(pos);
        return new Champion(champion.name, champion.image);
    }
    public static Champion getChampion(String name){
        for(int i = 0; i < ApplicationClass.champions.size(); i++){
            Champion champion = ApplicationClass.champions.get(i);
            if(champion.name == name){
                return new Champion(champion.name, champion.image);
            }
        }
        System.out.println("null champ");
        return null;
    }

    public static void championSetting(){
        ArrayList<Champion> champions = ApplicationClass.champions;
        champions.add(new Champion(0));
        champions.add(new Champion(1));
        champions.add(new Champion(2));
        champions.add(new Champion(3));
        champions.add(new Champion(4));
        champions.add(new Champion(5));
        champions.add(new Champion("가렌", R.drawable.garen));
        champions.add(new Champion("갈리오", R.drawable.galio));
        champions.add(new Champion("갱플랭크", R.drawable.gangplank));
        champions.add(new Champion("그라가스", R.drawable.gragas));
        champions.add(new Champion("그레이브즈", R.drawable.graves));
        champions.add(new Champion("나르", R.drawable.gnar));
        champions.add(new Champion("나미", R.drawable.nami));
        champions.add(new Champion("나서스", R.drawable.nasus));
        champions.add(new Champion("노틸러스", R.drawable.nautilus));
        champions.add(new Champion("녹턴", R.drawable.noctume));
        champions.add(new Champion("누누와 윌럼프", R.drawable.nunu));
        champions.add(new Champion("니달리", R.drawable.nidalee));
        champions.add(new Champion("니코", R.drawable.neeko));
        champions.add(new Champion("다리우스", R.drawable.darius));
        champions.add(new Champion("다이애나", R.drawable.diana));
        champions.add(new Champion("드레이븐", R.drawable.draven));
        champions.add(new Champion("라이즈", R.drawable.ryze));
        champions.add(new Champion("라칸", R.drawable.rakan));
        champions.add(new Champion("람머스", R.drawable.rammus));
        champions.add(new Champion("럭스", R.drawable.lux));
        champions.add(new Champion("럼블", R.drawable.rumble));
        champions.add(new Champion("레넥톤", R.drawable.reneton));
        champions.add(new Champion("레오나", R.drawable.leona));
        champions.add(new Champion("렉사이", R.drawable.reksai));
        champions.add(new Champion("렝가", R.drawable.rengar));
        champions.add(new Champion("루시안", R.drawable.lucian));
        champions.add(new Champion("룰루", R.drawable.lulu));
        champions.add(new Champion("르블랑", R.drawable.leblanc));
        champions.add(new Champion("리신", R.drawable.leesin));
        champions.add(new Champion("리븐", R.drawable.riven));
        champions.add(new Champion("리산드라", R.drawable.lissandra));
        champions.add(new Champion("마스터이", R.drawable.masteryi));
        champions.add(new Champion("마오카이", R.drawable.maokai));
        champions.add(new Champion("말자하", R.drawable.malzahar));
        champions.add(new Champion("말파이트", R.drawable.malphite));
        champions.add(new Champion("모데카이저", R.drawable.mordekaiser));
        champions.add(new Champion("모르가나", R.drawable.morgana));
        champions.add(new Champion("문도 박사", R.drawable.mundo));
        champions.add(new Champion("미스 포츈", R.drawable.missfortune));
        champions.add(new Champion("바드", R.drawable.bard));
        champions.add(new Champion("바루스", R.drawable.varus));
        champions.add(new Champion("바이", R.drawable.vi));
        champions.add(new Champion("베이가", R.drawable.veigar));
        champions.add(new Champion("베인", R.drawable.vayne));
        champions.add(new Champion("벨코즈", R.drawable.velkoz));
        champions.add(new Champion("볼리베어", R.drawable.volibear));
        champions.add(new Champion("브라움", R.drawable.braum));
        champions.add(new Champion("브랜드", R.drawable.brand));
        champions.add(new Champion("블라디미르", R.drawable.vladimir));
        champions.add(new Champion("블리츠크랭크", R.drawable.blitzcrank));
        champions.add(new Champion("빅토르", R.drawable.viktor));
        champions.add(new Champion("뽀삐", R.drawable.poppy));
        champions.add(new Champion("사이온", R.drawable.sion));
        champions.add(new Champion("사일러스", R.drawable.sylas));
        champions.add(new Champion("샤코", R.drawable.shaco));
        champions.add(new Champion("세나", R.drawable.senna));
        champions.add(new Champion("세주아니", R.drawable.sejuani));
        champions.add(new Champion("세트", R.drawable.sett));
        champions.add(new Champion("소나", R.drawable.sona));
        champions.add(new Champion("소라카", R.drawable.soraka));
        champions.add(new Champion("쉔", R.drawable.shen));
        champions.add(new Champion("쉬바나", R.drawable.shyvana));
        champions.add(new Champion("스웨인", R.drawable.swain));
        champions.add(new Champion("스카너", R.drawable.skarner));
        champions.add(new Champion("시비르", R.drawable.sivir));
        champions.add(new Champion("신짜오", R.drawable.xinzhao));
        champions.add(new Champion("신드라", R.drawable.sindra));
        champions.add(new Champion("신지드", R.drawable.singed));
        champions.add(new Champion("쓰레쉬", R.drawable.thresh));
        champions.add(new Champion("아리", R.drawable.ahri));
        champions.add(new Champion("아무무", R.drawable.amumu));
        champions.add(new Champion("아우렐리온 솔", R.drawable.aurelion_sol));
        champions.add(new Champion("아이번", R.drawable.ivern));
        champions.add(new Champion("아지르", R.drawable.azir));
        champions.add(new Champion("아칼리", R.drawable.akali));
        champions.add(new Champion("아트록스", R.drawable.aatrox));
        champions.add(new Champion("아펠리오스", R.drawable.aphelios));
        champions.add(new Champion("알리스타", R.drawable.alistar));
        champions.add(new Champion("애니", R.drawable.ani));
        champions.add(new Champion("애니비아", R.drawable.anibia));
        champions.add(new Champion("애쉬", R.drawable.ash));
        champions.add(new Champion("야스오", R.drawable.yasuo));
        champions.add(new Champion("에코", R.drawable.eco));
        champions.add(new Champion("엘리스", R.drawable.elise));
        champions.add(new Champion("오공", R.drawable.wukong));
        champions.add(new Champion("오른", R.drawable.ornn));
        champions.add(new Champion("오리아나", R.drawable.orianna));
        champions.add(new Champion("올라프", R.drawable.olaf));
        champions.add(new Champion("요릭", R.drawable.yorick));
        champions.add(new Champion("우디르", R.drawable.udyr));
        champions.add(new Champion("우르곳", R.drawable.urgot));
        champions.add(new Champion("워윅", R.drawable.warwick));
        champions.add(new Champion("유미", R.drawable.yuumi));
        champions.add(new Champion("이렐리아", R.drawable.irelia));
        champions.add(new Champion("이블린", R.drawable.evelynn));
        champions.add(new Champion("이즈리얼", R.drawable.ezreal));
        champions.add(new Champion("일라오이", R.drawable.illaoi));
        champions.add(new Champion("자르반 4세", R.drawable.jarvaniv));
        champions.add(new Champion("자야", R.drawable.xayah));
        champions.add(new Champion("자이라", R.drawable.zyra));
        champions.add(new Champion("자크", R.drawable.zac));
        champions.add(new Champion("잔나", R.drawable.janna));
        champions.add(new Champion("잭스", R.drawable.jax));
        champions.add(new Champion("제드", R.drawable.zed));
        champions.add(new Champion("제라스", R.drawable.xerath));
        champions.add(new Champion("제이스", R.drawable.jayce));
        champions.add(new Champion("조이", R.drawable.zoe));
        champions.add(new Champion("직스", R.drawable.ziggs));
        champions.add(new Champion("진", R.drawable.jhin));
        champions.add(new Champion("질리언", R.drawable.zilean));
        champions.add(new Champion("징크스", R.drawable.jinx));
        champions.add(new Champion("초가스", R.drawable.chogath));
        champions.add(new Champion("카르마", R.drawable.karma));
        champions.add(new Champion("카밀", R.drawable.camile));
        champions.add(new Champion("카사딘", R.drawable.kassadin));
        champions.add(new Champion("카서스", R.drawable.karthus));
        champions.add(new Champion("카시오페아", R.drawable.cassiopeia));
        champions.add(new Champion("카이사", R.drawable.kaisa));
        champions.add(new Champion("카직스", R.drawable.khazix));
        champions.add(new Champion("카타리나", R.drawable.katarina));
        champions.add(new Champion("칼리스타", R.drawable.kalista));
        champions.add(new Champion("케넨", R.drawable.kennen));
        champions.add(new Champion("케이틀린", R.drawable.caitlyn));
        champions.add(new Champion("케인", R.drawable.kayn));
        champions.add(new Champion("케일", R.drawable.kayle));
        champions.add(new Champion("코그모", R.drawable.kogmaw));
        champions.add(new Champion("코르키", R.drawable.corki));
        champions.add(new Champion("퀸", R.drawable.queen));
        champions.add(new Champion("클레드", R.drawable.kled));
        champions.add(new Champion("키아나", R.drawable.qiyana));
        champions.add(new Champion("킨드레드", R.drawable.kindred));
        champions.add(new Champion("타릭", R.drawable.taric));
        champions.add(new Champion("탈론", R.drawable.talon));
        champions.add(new Champion("탈리야", R.drawable.taliyah));
        champions.add(new Champion("탐 켄치", R.drawable.tahmkench));
        champions.add(new Champion("트런들", R.drawable.trundle));
        champions.add(new Champion("트리스타나", R.drawable.tristana));
        champions.add(new Champion("트린다미어", R.drawable.tryndamere));
        champions.add(new Champion("트위스티드 페이트", R.drawable.twistedpate));
        champions.add(new Champion("트위치", R.drawable.twitch));
        champions.add(new Champion("티모", R.drawable.teemo));
        champions.add(new Champion("파이크", R.drawable.pyke));
        champions.add(new Champion("판테온", R.drawable.pantheon));
        champions.add(new Champion("피들스틱", R.drawable.fiddlestick));
        champions.add(new Champion("피오라", R.drawable.fiora));
        champions.add(new Champion("피즈", R.drawable.fizz));
        champions.add(new Champion("하이머딩거", R.drawable.heimerdinger));
        champions.add(new Champion("헤카림", R.drawable.hecarim));

    }

}
