package com.yiyiaddon.e.o.c;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class c extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final String wP = (String)com.yiyiaddon.m.b.a<"s3kmpvx8e5qvin","TB9fqjIuztoD/7VF2FqMNYpfJ6bQjqRBMRSde8GRd5OKGEFCZYkX5Zxt59TD8D7G8fBB9xx5TUENQHu1XtNqAflou7r9DT8iSMJWH4pMUZCgrg==",-2349597481781787453,-748713452372971568,1107395706675192630,3508732838476032783>();
   private static final int pz = 20;
   private static final float dA = 6.0F;
   private static final float dB = 11.0F;
   private static final float dC = 10.0F;
   private static final float dD = 12.0F;
   private static final float dE = 6.0F;
   private static final float dF = 6.0F;
   private static final float dG = 320.0F;
   private final com.yiyiaddon.e.o.b a;
   private final Set<String> ao = new HashSet<>();
   private final com.yiyiaddon.e.o.c.c.a a = new com.yiyiaddon.e.o.c.c.a();
   private com.yiyiaddon.e.o.c.c.d a = com.yiyiaddon.e.o.c.c.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.o.c.c.b a = com.yiyiaddon.e.o.c.c.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snqbhylryu9y5","Riq1IudtAfAwW7mEOQP+ZrYiJcBnkrpT6tFnU0ISTYI=",-5707325948230624763,2951620857361284030,7899327133494190265,-2434048011970657403>()) {
            case 1613564892:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ni6z9frh6ovw","5bjPkRH9mx65+yQ2RvHqVRPgyU6VeLqp4kSRqq5Vots=",-802459459745879578,-6855391932507677290,-8264433310646298448,4075405014250387412>()) {
                     case 1255537543:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s2m9wfw04r90qa","fEeG45/vVAu7QjxI4L3WSMlBI0nLj6t4YM8T3HZwFzM=",9053117669932632977,-7472958011253869809,48075016721339975,6161654959480684861>()) {
                           case -844819028:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3kbwt5nnljkg6","74Z13dG060aezv9+YIJ8dykM5rkHBPYtlql6AypQ/JY=",3934340614689069103,-5333748899702577405,3643544938546135753,1902666190057982905>()) {
         case 1988049804:
            return 16777215;
         default:
            throw null;
      }
   }

   public c(Screen var1, com.yiyiaddon.e.o.b var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s207opcrdyb4a8","Ye3U47yst1l1OtUApPgsV36hEygROahI8gNKk/uR6oY4Qpu2MgTgzNei",6990381541655474228,4057856892075733243,7416627397817303324,8680786015165394708>(),
         var1
      );
      this.a = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.ao;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s3kmpvx8e5qvin","TB9fqjIuztoD/7VF2FqMNYpfJ6bQjqRBMRSde8GRd5OKGEFCZYkX5Zxt59TD8D7G8fBB9xx5TUENQHu1XtNqAflou7r9DT8iSMJWH4pMUZCgrg==",-2349597481781787453,-748713452372971568,1107395706675192630,3508732838476032783>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ehsu2nb0fuau","JqT8zhcQc18y62kpBHnH6heZLCYi6aN/zHbREOHXwYE=",-8015120285058598710,-5753364785622085503,2031168281607547481,-3344143650566630144>()) {
            case 890318480:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2hqxf64ozbxo3","L4FoTSYZJDWcu34VQKr9lYaxdoObjQg9jSjIn/Tks4U=",-2647566197398016104,6165390969341929745,7233234197979428823,-1158907846972011931>()) {
                  case -459589280:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public void removed() {
      com.yiyiaddon.d.a.b.h(
         (String)com.yiyiaddon.m.b.a<"s3kmpvx8e5qvin","TB9fqjIuztoD/7VF2FqMNYpfJ6bQjqRBMRSde8GRd5OKGEFCZYkX5Zxt59TD8D7G8fBB9xx5TUENQHu1XtNqAflou7r9DT8iSMJWH4pMUZCgrg==",-2349597481781787453,-748713452372971568,1107395706675192630,3508732838476032783>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"ssv6ad8vj62vi","3TYSt7Vhfe+ZXr19CFNaMrBjvMXnS1LNhk25wBpNLFg=",-8775514520364143397,-973659338820783412,422839605082646014,1221276146147628922>()) {
            case 2037397580:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sgfx1ek7hemnl","xvtFFE+mKEhJSiM9suEH1tCziavOnMhOrHX3hwXv5uw=",-3025176120030991481,-4490615330130040920,-5689591883690110235,5203800781650198033>()) {
               case 161646125:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sb68i4bdbgrth","pFGESkYFTSK2p8vRfbcgYt3eIY4M8i9XEF6XgSsrElI=",-4493733120320386684,650833518776689263,7301848897161092337,4717324497682560978>()) {
                           case -224223625:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.o.c.c.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s12r6yl8x36kqj","LJW1/w177gvAnIrslp3aur1NgNFxVt9mM2ycnO+rp3c=",-5616758380073489339,-8975457230352184568,-1690830071255147276,-2092809719604229905>()) {
                                       case 861185050:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.o.c.c.b.a(this.a);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s10y69vutffyv4","ayd2JZLSq9Vvr7ctQqIRNCQ9zUH7wkywYb8qu4XqFRI=",992467281449701187,-7293417609900560484,-1583858209198294188,-9083395289899699260>()) {
                                 case 1488997459:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sy4kr4wb9z9v7","LIJZJkAfVf1LoVnJZcXSdl9acaVHnTEpDwFACCvEowI=",4640295658192639369,-7418574546112002139,2178085965028234664,3790750606266083234>()) {
                        case -1567415187:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static boolean c(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjlv9780mhbgn","b+bhj6KnBPib9cB9YjHR5PwVL3qrwCO/CdIFKF7HbXc=",5646104284907725048,5159470647137747653,-6350997824845306812,-8340454878797139561>()) {
            case 42564796:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s8iyj81d8rzzl","0ThjdYqZzwqPL8ZdS9wCQ1kX5BqksBZ1Wm5QoaoJC88=",5096364624991929953,-3689707471289228018,-6626115201343369224,-3725084624825773041>()) {
                        case 405842553:
                           switch ((int)com.yiyiaddon.m.b.a<"s46nli6wlm5m9","VCyic6zehTooaoX810bUghAk3XRKqMHi38nVBltaJLU=",-6332122373243285974,-5131919204766689091,664579269206794447,828852218878632529>()) {
                              case -1551747709:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1phg7apnfp3a7","/vxzEZSmdxWc3AaZ5XnLHykFQ5dKnFJpRAp05QioKBY=",-3346084449860403384,-1905824012788398513,-170526417807251143,-2156706347687203711>()) {
                        case 685353337:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sbqty1s3cym0x","1Du9qDvlWMCFVx/NXpih57J9P8sOtnE2yx7BNapBeOo=",-1485503858707680753,9042369851719916024,-8223519936977679761,-5217718800294812957>()) {
                     case -561830469:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   public void C() {
      this.a = com.yiyiaddon.e.o.c.c.b.a(this.a);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.o.c.c.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2l9h29bdptgs9","KiAs2/wm/LLjEKT0VcFzzSgJjgl6e0dgV5Yz8Cx9P2Y=",8609240551695230980,2027340888213028416,5438758138255852089,7931293312831656158>()) {
            case 2109163556:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1233u3x5s7uw","msyXlTdd2Fsd6Ll+cq8+i+YT2sQlQxRpudxwOBhVU0A=",5834138482002833511,4773750979641420490,-7493144754282367548,-3190704530677096186>()) {
                  case 919690788:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1vbybcmiw00e9","xJil8TUcecxTSLFmKiJi38SJ9u5K6aiCI0yC87mBZK0=",5146933600118804352,-3091514978748855847,9208055240992076713,-6725994817661102778>()) {
            case -196258095:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.a));
      var1.a(new com.yiyiaddon.e.o.c.c.c());
      this.b(var1);
      label19:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.o.c.a.c(this, this.a).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1ct3uxqgq1d4e","OS27thYDr2rg0VoqLrRVSM/EYTEYVTjqZRA9j+7wyEU=",-2102099907994499495,-5674816203009317781,-1732979860302225687,-4037818102514183401>()) {
               case 245129634:
                  break label19;
               default:
                  throw null;
            }
         case MODE:
            new com.yiyiaddon.e.o.c.a.d(this, this.a).B(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s239bnkaj0m1lw","AAv123QlqWocBv+Py+W7olqYHiC5csjqj/VvzLXZXwE=",-4144490381221463660,-5989185195798718110,-7741410920779211045,-3632676174390492940>()) {
               case -217317141:
                  break label19;
               default:
                  throw null;
            }
         case PARAMS:
            new com.yiyiaddon.e.o.c.a.d(this, this.a).C(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1idjj4sv36l4u","zbRye/uqGqopCDgWnvUEZ9bcnPVEwJTXncaTuZw6Sdc=",8854806294100204779,1307315310996238562,7257920330713286398,-5826412956425482707>()) {
               case -2081002080:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.o.c.c.d var6 : com.yiyiaddon.e.o.c.c.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbaxzefuzju6e","EiHnefB2kbimHiROTDAQOJ5vVmc1jnmrR2xjkragIz8=",2960793130229473057,-8746958715958693330,-1789145923907126617,-8212536939396501756>()) {
                        case -1022277742:
                           switch ((int)com.yiyiaddon.m.b.a<"s3e43hxlg48euo","zpAzHzdmHPXQp1vDhQ/m878i5wyjHoCPG+cZYCYHqT4=",-4618653891530405636,3371220864208613176,5415278680220463428,2307120299763609654>()) {
                              case 1333884019:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3b0ynv74hudzu","qI6A+OOYuoi3x/fWQtAksDAQJ8/ZG1NyqfQ1m609jS8=",-2563693535280009065,-2140312222932460920,-7127205491793395143,-5614894561226076168>()) {
                        case -1322506698:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               }
            )
         );
      }

      var1.a(new com.yiyiaddon.l.c.f.a(this, var2, 24.0F));
   }

   private void c(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2sb3fw7hq4nb","s35/0npSId8EOJ+sgnY59xF5HoNoIbzIpOxx7t7uUZKSEDgj",-7956141901035472020,-234486028494708048,4629691225521212538,7107856431931134130>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"simqfp1azymtn","abnpDuNUpEzTzKNTVVFOYNoAbSrrBtxwVUeyGghIRrMGLwds3hQCz/b55TcKG0Tyxk5a8zCK/pew4WiH6RJMQaf1PRCZ4uzC3gxlHiSL33k2CGRNKw3kWxTLnZuThSR4jN81Vw==",6264492680264933307,967004191919041092,-4582258358070297644,1296479687774450315>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.a, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3mv3j1oqjwypd","qLOblBSBAudwNmF75DIr6iHZj6X3WeeKiCra83j7nZvYtqxH",4488612910853441347,-3422665847969238551,-1251358922920646353,-7691424596431472233>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"synwuqj2evqz","YchcFVv6LDjGwz15HCUldm6f/CmVnmWGNCwt4WPbpFI=",-5844234836057580789,2385247859409924540,6243073147657199743,7313667166233423985>()) {
                              case -1133714360:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2heqqoj2g8e0","gs9vCyYGp9TA/cQ5hfVGE91WqALtllM4uOYsFr5tyIc=",-3726368087198525599,-6222223219454793593,8911855190339107712,-274624823629168977>()) {
                                    case 1628355618:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
               )
            ),
            24.0F
         )
      );
   }

   private static List<String> a(String var0, float var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var0.split(
         (String)com.yiyiaddon.m.b.a<"sh8nojz6jmbn9","MYXPx2gHqyapB+j+a2gQwJYm2+ookUQKMa6a3dAZ",3546288239854782197,-8762177622411656679,9008246365951811792,-7760184289513472660>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2yxdfal8p75s2","4HCpig7kh9brR/FCIc7DfjvSo6qSEh0gXEd6xjVPRHQ=",-9191456566543490381,2814425645272141140,-5323561465460838863,4430743886835960598>()) {
         case -1119372371:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s21r4beccw1kv5","puUlATVLORBqQybnDWAhLXJUSiFW7IXMRIyMxTyX2KI=",-3337142676309610908,6667203640749013668,-3663912480265168837,-795300563555631155>()) {
                  case -755760155:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s3tz48ylb3hbqc","ybri09dBa/TE0RqK98U2tv72F8hEGZn/Q7eEvnKw5gM=",-8717104213960804301,2879744121924139920,5512924531946244530,903128741263883289>()) {
                           case 1777708323:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s1cwcsk5zryadp","ueGwuWLNTQMZm7OJobs/D2thdfx5uyPEwdyZ7Q==",-4960184216215385574,-2644005330057323571,-8937599898936811310,-8049587380128048395>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s22mciuv33j1u","4aN7lcsiIQbOqykJCrO2zt2yXz25pdFhrWPMhXHPDGA=",6935318373605280323,-8081133125308177305,6957020950151686054,9212747702359123237>()) {
                                 case -406031475:
                                    break label68;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        StringBuilder var7 = new StringBuilder();
                        float var8 = 0.0F;
                        int var9 = 0;
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s1nxrrnrd1uz30","YbB5tLv0InjG0VtVwALQ6g6w9s2JJ5WHEnQhoN96rRg=",6643810810821724382,-383787772538446408,-7871701126314830680,2226036534215184751>()) {
                           case -1274473817:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2hh2juwxzebi9","FyMYeqaYZA4L9LnS5Y8EM00ftVNV6PaNWOlIr9JiYZM=",-1492616945455454637,8124553979382017243,-4731697119329088450,-6005358392312751858>()) {
                                    case 1741314541:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2k9r1t55u9n5y","UTDr6Hm1g+7wWkI9sJPrpNVSDX99AuzqAsGbyie1E7k=",-8024921992353184004,3939168800399764450,-2634156998805766657,-5281848703795861830>()) {
                                             case -1022475883:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2qqbojae2ysd5","5NP4YzK+WcTF6RQWqtLkwF7ifSebe0xbSH8cKAIYk6A=",-7999382206881219036,-1848603092882225050,2927379797435972886,5712836208329658385>()) {
                                                      case 1024146196:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1gbhpbmwofj9z","L1B0Q/Rbfy+U996QJVXpgFzZW3G8wOoVq1ex3f5Omqk=",-542236809558857667,6522873678664046953,-356096822342445839,-6789451121130987205>()) {
                                                            case 574364715:
                                                               break label64;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       float var13 = com.yiyiaddon.l.g.d.a(var12, 10.0F, false);
                                       if (var8 + var13 > var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s13hshtue5s1si","py6WpNazeHufu/eSsE9JmEpqL8Pk210XEQlFTtwsqXk=",8409003097426027895,9004415055840624401,-1280893773013578245,-5431532291169549031>()) {
                                             case 1382582108:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"syih7qqz5771j","iVifBpUktRN1/+0nufwVjY6uNnexrQWXdbRW0kxFXqY=",6797716640845839748,4499206430631465717,-7364111072710642220,-1104806587505135691>()) {
                                                      case -743766916:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s27qza3412nits","qpVpF2WGrpElBHRtLibtoNBIwIdoKRpyqZuT9fwM9js=",7328603498650277106,-7675501475055579880,-3292621939590863580,-7207935358342114451>()) {
                                                            case 1913816916:
                                                               break label58;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       var7.append(var12);
                                       var8 += var13;
                                       switch ((int)com.yiyiaddon.m.b.a<"snladquqn2zqp","EvNNWI8x6IcXZrcYvEF57MWoE/zVCxO/EcB98QfHs2Y=",8609751496115170776,4056278488613773979,-2481058931808926812,4776760400804774497>()) {
                                          case -1734363453:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2fligksxfiyrg","4G4FmSVJw6hLXrmywYtGUVAkoVnjq7mjfimV0Su4ayQ=",-1333598651002879694,9070759283149401503,-8827978857314945276,-1900748743738637063>()) {
                                 case 73566369:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3743tdxdqgoxn","CENNYeCLjcRWxdQDzggfjc+Cvd/iBjOWHdODSvT54/M=",-1178494210729438207,226530721697468244,78495089202353021,-2424268567161097564>()) {
                        case 1070204030:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   private final class a implements g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
         i var1 = new i(6.0F);
         c.this.a(var1);
         this.a = var1;
      }

      @Override
      public float b() {
         return this.a.b();
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var3, var3 + this.a.b(), var6, var7);
         this.a(var1, var2, var4, var5);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE, var6);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE);
      }

      @Override
      public void F() {
         this.a.F();
      }

      private void a(String var1, float var2, float var3) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1stncc6m4nzoi","zdb7PbbGBSrPMv4OW2a9/VfLjTO+N0Fpg6doY/9i+1Y=",-5767534108549752545,876521974129717010,-6876017912126282899,235714582355423625>()) {
               case 668881384:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1uielqzbp4sme","rbQe6vzYZRirc59oEzO7fzSYYiphNtC1mOD2IOjfp0A=",-4540778935753222013,7432834941216050426,-7218616336581365006,-2324256400294380843>()) {
                        case -1416398019:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      }

      private void a(Canvas var1, float var2, float var3, float var4) {
         String var5 = this.aW;
         this.aW = null;
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1il2f8n0xxue","AKnvzCM8MtkQrJa5rS+mpxM0rcv2EEdlp5ghPdC9K7Y=",-7963861082272264719,3688079786181578301,-4369848310151450793,-4435611429493957697>()) {
               case -1265170611:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.o.c.c.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"sg2b2tjxrqehv","p4+3aebiRh+ZK/M4+flfGONE9EO9dLmZtkiZLz6Jepw=",2831989360296508871,-6954896286609588762,8139786394314922756,-1588745504162700826>()) {
                        case -1773379973:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"shfpydnm8pck","cCN2ov3embBTnsveHSFzixXlTxY5s0XN/supukKAvDA=",-8323008791623401953,-5780053083285136345,5054889783576604137,-8930630166649579694>()) {
                                 case 1285091930:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2teangakvbez8","XszqFS0Un8VVIgaQMgNw74BmZRPhB+rF7hBeJjrMJvI=",-104552187193065980,1384169287936518288,2393105062687728315,-1188210147493250770>()) {
                                       case 571691951:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var8 += 12.0F;
                           float var17 = var7.size() * 12.0F + 12.0F;
                           float var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                           float var11 = Math.max(0.0F, this.n - var17);
                           j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.o.c.c.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s2n9qxncerg6ab","K7cfbCNjS/JslONl1sRTE0ceXh/s9XEZbiTC0t9fpDU=",-9088173789176715803,-693736480527582305,8695218696013534481,936224194340031246>()) {
                              case 1078448184:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3mkqjbarrj7zz","WyHLe6V+TxSoyROqACJDibA7k/nF++68K7i4dn2O2I4=",458062277799333312,-5020057378809906421,-8745860201114031528,-3824699928980793596>()) {
                                       case -1095680376:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3t4xpd26z9wt6","d8OV0uvDMaVY80IxGylcdHAP5y89fdWQbXrFADRnWcs=",-1990151682151317056,8643011048412011959,3128835359076172285,-2133076982439354785>()) {
                                             case 1272961369:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s930h7wlgrmvd","5CzRBpClRTzR1taqWHmkXc9Ik28eirTVSomN3QH3dEE=",-5536377033165567703,-1696123125861725783,6874684410791067374,-3874122039608516511>()) {
                        case 71696607:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static final class b {
      private final String[] Z;

      private b(String[] var1) {
         this.Z = var1;
      }

      private static com.yiyiaddon.e.o.c.c.b a() {
         return new com.yiyiaddon.e.o.c.c.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>(),
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>(),
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>(),
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>(),
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>(),
               (String)com.yiyiaddon.m.b.a<"s2mom035ksvho0","azom9KauwkERsEJ30d9guECvg16sX/ezUU/bgg==",8854155218055757192,-8255585591937826237,157770148118838434,7410940379739938925>()
            }
         );
      }

      private static com.yiyiaddon.e.o.c.c.b a(com.yiyiaddon.e.o.b var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.o.a.b var1 = var0.a();
         return new com.yiyiaddon.e.o.c.c.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s1ublle8yuiv1c","ySzunqiX4iarDo/yGGqqAxVP+SwGc9th0gW689fhpm5iWcpOBio=",-3248604875198059814,-8882024682778582517,-5040149257998367407,6052246784707592839>()
                        : (String)com.yiyiaddon.m.b.a<"s2ih4vmtp014gi","3cH7u2R/R9q3ZQfGf7BnXNpcidKtWiQUdnxH61lGfW2VzJyzoT4=",-4650551989694908794,-9031229889506584376,-29507764679468681,3164576513501640650>()
                  )
                  + "",
               var1.b.wH + "",
               (
                     com.yiyiaddon.e.o.b.c.eH()
                        ? (String)com.yiyiaddon.m.b.a<"s397bsg6xoepfj","w5Oyu7Md8xitJ4P354S/4SCUuUFDbQnxYdEo7KWqEj0h2yUpK46rmDZDV6YnGQ==",8277377767498959115,-7810450837955031256,-3527663132081993682,-7587406543904761930>()
                        : (String)com.yiyiaddon.m.b.a<"s3e2aklfo0j31v","yaiWfGk2LE4i16LrGVAOramSucrStUx3Lbh5lQBGfVAl6A==",3973187913936958093,7310135222716301372,2162354492416274748,-9171475628259046541>()
                  )
                  + "",
               com.yiyiaddon.e.o.b.c.cU() + "",
               as() + "",
               (
                     com.yiyiaddon.e.o.b.c.eI()
                        ? (String)com.yiyiaddon.m.b.a<"s3ezt69zdgal4u","qwU25PKGGuzegNlmvB23kK5TdvUx6G+6qUyPSCJlaE8/4SDm",873136531379911321,-984212866098413435,-5542396346156192758,4979674450562099496>()
                        : (String)com.yiyiaddon.m.b.a<"s1dg40q6j1e5q6","uaqMch7Qx8jF8gEI2X7qeMTbaMac69kp+2Tu3Vv0A802DqQw",5806434455357083532,-397107211196263836,8461679472241288691,-4692906057493773946>()
                  )
                  + String.format(
                     Locale.ROOT,
                     (String)com.yiyiaddon.m.b.a<"slc82upm6ku7v","IikmuCIYBgN2s891iBt1bQKRnwI+fOa6ePGFAxULp76g2AkR",1850920408040699292,1427711069788456538,-741660790078273144,-7917095534261344328>(),
                     com.yiyiaddon.e.o.b.c.g()
                  )
            }
         );
      }

      private static String as() {
         String var0 = com.yiyiaddon.e.o.b.c.eU();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s18iu2goah9ta1","L26AjLot7qeiGKNihB1StsvLix0TmBAQnkQ0Xjpselg=",-8595274758832661185,-1807889688592506687,-4809457823125754529,3521001344335583815>()) {
               case -949341402:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s39ji34y6n28rh","quqAO5MhQaCfy57mn9hxR4ss6cSU6NYV3dS2y7pltiQ=",-7653041646619491524,8667778028717487681,-5481251117155639590,7662209925074288292>()) {
                        case -739311337:
                           if (!(String)com.yiyiaddon.m.b.a<"s2mmgf9oh4ac9n","AshUoPLo/fmJyZi+HXCJNE6tbwPOVrNblb5fYXbDkXw=",-907475409034885843,-6946256339630741903,7044886189811629920,-2272549125072595980>()
                              .equals(var0)) {
                              if (!(String)com.yiyiaddon.m.b.a<"s326zkhpa4vqw8","xJaiqDAO2MivRsiz4KnwzUZOh6koc4hBu+wNAwE13GyG7w==",-3740220197839328008,-2783905489756704457,-1313824108583259947,-2696732127028841849>()
                                 .equals(var0)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sv4qd12hxctzp","yPLw7W9oVkomqEWhXYjgJPPwxiaS6xMOebX1NOQS2WM=",2752664611672242901,2549565156572923548,7704459222025102755,1998976293414929839>()) {
                                    case 768780388:
                                       if (!(String)com.yiyiaddon.m.b.a<"s1o7cxsty5sfdz","l42Io2rgDdr9LmIl/llNQiYu499uz7hElYcitRufLLoBoA==",313126598130706062,8148263159577747292,-2044809342041834031,4594355350331176150>()
                                          .equals(var0)) {
                                          if (com.yiyiaddon.e.o.b.c.eF()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"saq7knjrdoz1f","Gvt7bugfWt+63n8TXO5GXoL5yb9fTCoqLNILZBYhS5I=",-6791762248761199370,8806040992621042700,-7504142447721049067,6467839262825857228>()) {
                                                case -352559043:
                                                   String var10000 = var0 + "";
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1157g3faeqhxy","yJlrM4ArLFJzX/2xzrEpHzixNkrYUDsOwwo3gt2t7rE=",1134474924187716367,4311119409691304299,-8037243516895313925,5416418774985358490>()) {
                                                      case 140007785:
                                                         return var10000;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var1 = var0 + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"sgcgm3pulp9qz","/PL+080agJrehPbOhlFrb19NUARzgGn+rT75nm3u9Ag=",-821804445421490176,7762157406998794247,-284338238929040509,-7224766849121076845>()) {
                                                case 954518732:
                                                   return var1;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s19nuaibl9koym","9DO2azi/Wy9ZadiFtIhPaEp7V0xlWjyNFfG1vE+tyyQ=",5100107169707272264,2569606511471936959,665091694358168487,-3919903939778539719>()) {
                                          case 1282149839:
                                             return var0 + "";
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return var0 + "";
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s39blvdz04l42n","D213TfVZjGP64oL64JRzvHAQv2YeuYU7/BM3HhxDuYI=",3871462344603049877,-4331643403716223417,-8372966447076454675,4584203629847020116>()) {
                              case -463948618:
                                 return (String)com.yiyiaddon.m.b.a<"s1yngt6cbongfg","oBk6tLeKUUrn0pvuWFUxMvxciSJwIeQLFEvQ03CEI+bTpjN0",-3770782465404128707,6348966074536865694,7461627033617357244,-5188583307495450868>();
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return (String)com.yiyiaddon.m.b.a<"s1yngt6cbongfg","oBk6tLeKUUrn0pvuWFUxMvxciSJwIeQLFEvQ03CEI+bTpjN0",-3770782465404128707,6348966074536865694,7461627033617357244,-5188583307495450868>();
      }

      private String c(int var1) {
         return this.Z[var1];
      }
   }

   private final class c implements g {
      private static final float dH = 18.0F;
      private static final float dI = 6.0F;
      private static final int pA = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.o.c.c.b var8 = c.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3alq5cta7g43i","Fa0AHA/WIiOT3e1A0ekQMmp7jgI+ZhUW+X9v3ORxj8A=",3870054153168737191,7562662429732027029,2353495240190250316,7093562824079230055>()) {
            case -1360029854:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s5xet10y824lz","iv2nGFE4KqN6kOX+V2eVrnEiSkFWVrwHrO1UUpRaRWc=",-1968483620899000208,3443643044444701818,1679851724705060283,5940738258903825474>()) {
                     case -627749874:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"sca3av7vkk4y6","V1DesELxwoqwIcjhrlIdNtMHT33iiiLbc1laNi0wDwc=",-2657070648782969526,1206614536420431891,-2931772405719268538,7464207957138434600>()) {
                           case 1171244979:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6) {
         com.yiyiaddon.l.g.d.a(
            var1, com.yiyiaddon.l.g.d.b(var2, 11.0F, var5), var3, com.yiyiaddon.l.b.d.c(var4 + 9.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var6
         );
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return false;
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }

   private enum d {
      OVERVIEW(
         (String)com.yiyiaddon.m.b.a<"snm2xx5zrp6f0","CsnTjtBgFjv76uEDQVMieOu46D5opXc5ReUfzCjRql0=",-7755143224161652608,-5145131498518552980,8966812405412635569,-8870660651886196288>()
      ),
      MODE(
         (String)com.yiyiaddon.m.b.a<"s2apmii1f3zuuu","uoz684Cwb0x5Gwnv36HXfX1umdbbSHlymiN1vbLsNHeLOjB6",-4892483767279174659,-5055827855719539808,-8861784123524309950,2502213631718201702>()
      ),
      PARAMS(
         (String)com.yiyiaddon.m.b.a<"s14k5gu84lut8m","CJJdZdge4l5e9kZhsYtymReIOFc4hmqheGlyXwa1p1nPxi8+",6542749325163375710,-5312841893024500035,6985005303537454175,8643502819991332856>()
      );

      private final String wQ;

      d(String var3) {
         this.wQ = var3;
      }

      private String D() {
         return this.wQ;
      }
   }
}
