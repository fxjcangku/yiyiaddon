package com.yiyiaddon.e.i;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.i.d.f;
import com.yiyiaddon.e.i.d.h;
import com.yiyiaddon.e.i.e.c;
import com.yiyiaddon.e.i.e.d;
import com.yiyiaddon.e.i.f.j;
import com.yiyiaddon.e.i.f.p;
import com.yiyiaddon.l.f.i;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String lj = "librarian";
   public static final String lk = "自动图书管理员";
   private static final String ll = (String)com.yiyiaddon.m.b.a<"s29rr84f1tv4y9","fv7rNWqHkE5xQG2zQChGS7i/X2Uv+GHkcYGugc5c",-7366337380931547930,6552851895693276835,3742415183984962085,1318441935420113435>();
   private static final int eP = 80;
   private final Minecraft C = Minecraft.getInstance();
   private final com.yiyiaddon.e.i.a.b a = new com.yiyiaddon.e.i.a.b();
   private final com.yiyiaddon.e.i.a.a a = new com.yiyiaddon.e.i.a.a();
   private final com.yiyiaddon.e.i.a.b a = new com.yiyiaddon.e.i.a.b();
   private f a;
   private j a;
   private boolean by;
   private final Deque<String> c = new ArrayDeque<>(80);

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s3nejiixpqjkjm","w4FRjWK9aDmwSXI7lVQdtjCGY5uPHTXIiaoFQ+8q91N5d7PBgmIsHRCpDoCntA==",-2150482517106044360,-2585362625529509359,-7627147852768330511,-8502901587470414190>(),
         (String)com.yiyiaddon.m.b.a<"s3qtjliy48wykf","MI4iBMf+lWv7dJtP0JDxW3UGpxx7mRq8fN69a4QQGSM7lm04wt8599GD",2290159842115387471,8467887827277638254,-3412899176770718725,6201253149720373966>(),
         (String)com.yiyiaddon.m.b.a<"s3lk13hxja651r","enMIYLiPKu46k/Fkzvub2eC/VDAAUA4W4vY51L0uSN/w6A3QHm/qk/ooFh69aN/a",2074788449761908639,3077200755538150352,-1993404050566644655,-6937196275630351601>(),
         (String)com.yiyiaddon.m.b.a<"s26qkog586bl7a","IECqYF8ZYC4prU04mk29LG8trTlQo2QHb+ggXeIEj77T2PmV9ZdV43MYfsus7MlMAjvYEkuMLR/0c2AQozHx3X8aJxkFyqq/0vRipUy60C+X4m5Hj56dLZMW7zJMvBzmDHi6OZORz4AuTulPfOgBG+wOS1ymmZd5cIJWrxspGxs=",7948598531659480371,-4899712796562013063,-4845387000147890007,9218755394352288904>()
      );
   }

   @Override
   public String a() {
      return (String)com.yiyiaddon.m.b.a<"s3nbl2smaqipmk","SqlisgcJeDQxigdsBDt+1kU29eIoWfHi6BCN0nYzHn2U+3BJFZ0vHp308T0mOJcKfvHMUwWX",2401276641134911578,2745860868766069306,-3417478111387630958,-2849931125095386864>();
   }

   @Override
   public int i() {
      return 60;
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s29rr84f1tv4y9","fv7rNWqHkE5xQG2zQChGS7i/X2Uv+GHkcYGugc5c",-7366337380931547930,6552851895693276835,3742415183984962085,1318441935420113435>();
   }

   @Override
   protected boolean i() {
      return true;
   }

   public com.yiyiaddon.e.i.a.b a() {
      return this.a;
   }

   public com.yiyiaddon.e.i.b.a a() {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s120wwaeb42j51","g4FBhi5zpo7mpLe8YiAGO+TkSLS8fKIpNdKQ/blq0KU=",-7416623559013507591,2476931056001731100,3608738830336287692,6595221342338747746>()) {
            case 704916928:
               switch ((int)com.yiyiaddon.m.b.a<"s2hpbiprw0hnwh","dPhPY/FCQkQ20MjSQ/+PFm8gUhxhabpgOWCJTGMDzAA=",5234212590250025870,6619428141521541248,-8146750178869216603,447620283874506280>()) {
                  case 912549708:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.i.b.a var10000 = this.a.d();
         switch ((int)com.yiyiaddon.m.b.a<"s1djr116bp0kso","907SQ+0t6F+7hmuQxrePg00/f1iFA6Kq1AQwHgrwTlE=",-3907132672019173797,3631161883633336279,-4901730333589743159,-711110743602976710>()) {
            case 331461611:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public f a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      e.d(this);
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.i.g.b(this);
   }

   public synchronized List<String> ac() {
      return new ArrayList<>(this.c);
   }

   public synchronized void dC() {
      this.c.clear();
   }

   public String ae() {
      com.yiyiaddon.e.i.b.a var1 = this.a();
      String var10000;
      if (!this.g()) {
         label37:
         switch ((int)com.yiyiaddon.m.b.a<"s2a3s0hj4su60u","Phb0SpFompe22ypYZgwWNeOsDE7DvLovnZj9WQ7qiiI=",-6827494599013639812,7354902969304751663,-7170150994503648940,3976814229883559001>()) {
            case 935758553:
               var10000 = (String)com.yiyiaddon.m.b.a<"s2bt8sgyzskj9f","/YJX/z0uaedf6OOlTVQg8YRoRGkys66lz6tpJDw6Rn+z9NAK4bs=",6405449836840697286,60062741007095391,-5591078134682567934,-480544552672217147>();
               switch ((int)com.yiyiaddon.m.b.a<"sugr626zs574x","+/w1QWhY8le21aw36Ens25U+4x9LUMgP/tvRDV6E/n0=",-5201406090913098602,3855451826727605021,-2349905378650541634,5881369915155333523>()) {
                  case -945333692:
                     break label37;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var1 == null) {
         label34:
         switch ((int)com.yiyiaddon.m.b.a<"s1g1hdu72dq8rb","FSUib/WXS8LV0uCbScsUD0WHYKIVpqqkR7nd3YVqge0=",4442582610813622625,8576870331697542701,808630193414612365,-4438296755190760510>()) {
            case 1397786834:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3gxsvqhactfg0","0JSpw1akFHBq71bQclBiGnfYMumlcGUoQ3wXSUm8rP9z0zoNNqzzYw==",847213003534037571,304313480179878857,4085170216824662928,-7481567507311707242>();
               switch ((int)com.yiyiaddon.m.b.a<"s38cls0486riex","kyZtkL2L5A06fIPaLdUcVBYKZu5X8V7022Xvqn79lZA=",1573665320138768105,-8388387015841520151,5496657609323389978,658273337261959888>()) {
                  case 699854947:
                     break label34;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.m() + "";
         switch ((int)com.yiyiaddon.m.b.a<"s1ehns9g6xkgpf","rIcjIN0aZc7fuggojKppJ1xHUS3tGwbeLPpZcElWlxU=",-9164763804362609054,-8566748472906342375,7536761114898848359,6484919703846187072>()) {
            case -1141711941:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      int var3 = this.a.af().size();
      if (var3 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2w7qdluke2x7a","hHASjOCoWANYd8pvgp10vnEwAZCnsuieNeFiZhQ64/Q=",7853302192048088554,-7051549896395306418,7516114474579517874,-1306631215792103948>()) {
            case -1665854217:
               String var10001 = (String)com.yiyiaddon.m.b.a<"s1a9xw3n4ascfh","wtsW2g0nKB8UCpw4hHrMJ6918iXguahYEIkcDB6yoIRKMw==",2476241766854167194,2174626554729192769,5833156332332945969,-6117127035917211298>();
               switch ((int)com.yiyiaddon.m.b.a<"s1bhepa4a19cll","vHLnjCsm57yDJepp9e+b7327q9AQP9wuBE26q0wawQ4=",-4138317303832332067,-4511899624483870886,-8658072295511827442,-6785801924056482333>()) {
                  case -661820893:
                     return var2 + var10001 + this.a.fj + this.a.eS;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var4 = var3 + "";
         switch ((int)com.yiyiaddon.m.b.a<"s3nezmdoxamc1","J+JDGctg0yuUFHFJdAvCn9qLxLyjMUOkxRGdrgAfpUM=",-1781308283652447668,-1147679237966151679,3021041226469831871,-3909358311562914926>()) {
            case 513531945:
               return var2 + var4 + this.a.fj + this.a.eS;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void m() {
      this.by = this.a.a.fV();
      List var1 = this.ad();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iaru4yrzrumz","0vG0BplV0ooJYq7Zkj1ZPDm0CR8jipUxQ4c86zbD2NM=",-6581074309510553566,-9219445068686002911,-1418712783540181740,-8407983590877818778>()) {
            case 1313912951:
               this.X(
                  String.join(
                        (String)com.yiyiaddon.m.b.a<"s3lx9o133ig9i6","/PUAxZbUBrThwqZ5PzvTNmOCoVYAEK6hTUL4NfNE3bg=",7303389491037154983,-8299186823824796981,5082679809105525211,-3349298922020622653>(),
                        this.a.af()
                     )
                     + ""
               );
               this.dD();
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.i.a.a var2 = com.yiyiaddon.e.i.a.a.a();
         com.yiyiaddon.e.i.a.a var3 = new com.yiyiaddon.e.i.a.a(
            var1, this.a.fj, var2.aO(), this.a.eS, this.a.fk, var2.aR(), var2.aS(), var2.aT(), this.a.P, this.a.fl, this.a.bC, this.a.as, this.a.bD
         );
         this.a = new f(new h(var1));
         this.a = new j(
            var3,
            this.a,
            new com.yiyiaddon.e.i.e.e(this.a, () -> this.a.as),
            new d(),
            new com.yiyiaddon.e.i.c.a(this.a),
            new com.yiyiaddon.e.i.e.b(),
            new c(),
            new com.yiyiaddon.e.i.e.a(),
            new com.yiyiaddon.e.i.f.f(),
            this.a,
            this.a
         );
         this.a.aR();
         this.h(var1);
      }
   }

   @Override
   protected void n() {
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s3hph08vn81cyb","6jCl03qNxmOEVpoCwUDwWl7ouONLqQ+ENne1KEimN8Y=",-405541357626926861,7564576035844975764,-3160896225971771631,-253072321458266262>()) {
            case -1849074256:
               this.a.ag();
               switch ((int)com.yiyiaddon.m.b.a<"s2mw7xkegbap3r","PZyALOh858RIeqZ9SwVq7ugrFjCV+cDZhJB3GIlZh/c=",-4458750310177870229,-535540747106294568,6366477586671984256,-8087150584654329983>()) {
                  case -1104115069:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a = null;
      this.a = null;
      this.by = false;
   }

   private void dD() {
      this.C
         .execute(
            () -> e.b(
               (String)com.yiyiaddon.m.b.a<"s3nejiixpqjkjm","w4FRjWK9aDmwSXI7lVQdtjCGY5uPHTXIiaoFQ+8q91N5d7PBgmIsHRCpDoCntA==",-2150482517106044360,-2585362625529509359,-7627147852768330511,-8502901587470414190>(),
               false
            )
         );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.SCREEN_OPEN);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ukzlzf35fdwx","2x+BB+WlPg7Q1F0d3jM+67J3+bSEp4Dw3XoS4OZkL6A=",643720495939346880,-5010512155018253055,4109153451571411947,-5370694759715995904>()) {
            case 1693918159:
               return;
            default:
               throw null;
         }
      } else if (var1.a() == com.yiyiaddon.d.a.c.SCREEN_OPEN) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xdtanzcer5ow","3H/UkcS9M3i8HLH2R5yQJWyi/aRkXAwoQImFTd0KVNc=",-8657163281586960069,5838861194255314986,-5481151268982295807,6182349292799731333>()) {
            case 509821059:
               this.i(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3ifjsr7xgp87e","cnwWNPFF59soZOf7AgmnssXgNV98rfhDjapFzkUuMOE=",-2701308635776870033,6563863006288087954,7958940169491276571,-862060883934569633>()) {
                  case 1110000985:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      if (this.C.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"spyuek1m9544u","rfvQEC5Zw7X7chSCv1EL7OOfywMUUpJ7IYIsWKq+lvM=",-4269761232656636991,213723303601611735,-3123651654858210081,5318609567015202817>()) {
            case -1077971133:
               if (this.g()) {
                  String var2 = var1.l();
                  if (InventoryScreen.class.getName().equals(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2oyzpfo84e786","Mv+uiylD1xMDt4RKLriagBlkYOj2h++2Xy374Vk1zLI=",2941677370452371846,6910794627293836899,4006738316859882855,1462818758957242047>()) {
                        case -2011961285:
                           return;
                        default:
                           throw null;
                     }
                  } else if (CreativeModeInventoryScreen.class.getName().equals(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2mjtnxrgmdzx1","GndE7jrQROO+EuayogFCJ+mnekY5thiROj9PCFOuDfQ=",5008823496389399312,-7958274728881783275,-226257192018967471,7871611303179815283>()) {
                        case -1504779440:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (m(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2d6w44kgumost","Bs8PwHRYznMEhh8p9o0tDdsbEi/5KIwYpSUFufFaYVg=",7009145392723300934,2288380217829962803,7732819931967218261,2843625729733535280>()) {
                           case -243871213:
                              var1.i();
                              switch ((int)com.yiyiaddon.m.b.a<"s3frm0ne2yohhg","6ROAjM0mlANoFxAZAKcuMd1Pc/SGDFEEzogJHRmDKBA=",4745179581225957469,-8668104419203007656,-2508661617205133856,2972994467593474918>()) {
                                 case 1803985752:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s35klzqw2rrw55","TZYhGlmXK7c8ZeDLlSJOC5qbhX5LRe60gEr5kD7Pgjk=",-6870545277666809861,-632144831747470706,-8855762210503790046,6148230460036113813>()) {
                     case 801505866:
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

   private static boolean m(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(var0));
         } catch (Throwable var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void b(Minecraft var1) {
      boolean var2;
      boolean var10000;
      label44: {
         var2 = this.a.a.fV();
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3kfq08pxxbtay","GzeFfmALC/Vqni+7BLcE9uFsXIxFanSOTMVXBtcNnSs=",2429688292943337575,8051929623085551694,2169596692472619800,6928465287094325561>()) {
               case 2005854498:
                  if (!this.by) {
                     switch ((int)com.yiyiaddon.m.b.a<"szlisrfvrolsz","bVnjX0XQT73ING/hDDzp0a5NTpaCKyDzNbsOMXj6v6M=",8621451001627867023,5924539351742065350,-1691652174502610649,-8809370807031269409>()) {
                        case 1225426964:
                           var10000 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"swu18mlsjodpw","GE3LpHScllVZJYos3ui79PrW9WzAYMj7LTVswcmqb0Y=",563054888365576050,4893851273269289586,9118754253875484325,2877214706915395762>()) {
                              case -834363197:
                                 break label44;
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

         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s18no1us35a588","MHa+KsGtBH72TIQ3Lud/NT2rz8TBxN3WJXgakwJJR7w=",8289859927463352499,1004307635208010366,-4264422364016760044,-8063813182652072345>()) {
            case -1988849261:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = var10000;
      this.by = var2;
      if (var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s2o1vu2674nzgb","n3vZAE/w6tRX+sX4J6EgntCX5RrpPdwSReNftc1v7Cg=",7496630257029860522,733987909433476895,2293005803127558934,1869719866536058260>()) {
            case -1288516678:
               this.X(
                  (String)com.yiyiaddon.m.b.a<"s3j8scpgdodx5c","uRMqEx7iTxB8A2T6HfXh1cVfaaT9SkNJaMnPmf5BpDPN+oJtXPk9YgoDbpcQ6w==",5322017324587382358,4453321181108292889,-7235535979883555073,-4613486292654889151>()
               );
               e.b(
                  (String)com.yiyiaddon.m.b.a<"s3nejiixpqjkjm","w4FRjWK9aDmwSXI7lVQdtjCGY5uPHTXIiaoFQ+8q91N5d7PBgmIsHRCpDoCntA==",-2150482517106044360,-2585362625529509359,-7627147852768330511,-8502901587470414190>(),
                  false
               );
               return;
            default:
               throw null;
         }
      } else if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ltqhpw9j36l5","d+WAX8Axu/wpfxwRNUt/CfBUDt8Ncrym/WINJ6PbPoA=",-8059262654451934787,4527262016455948216,-2790550933001803965,4482644309371322929>()) {
            case -753558014:
               this.a.ae();
               switch ((int)com.yiyiaddon.m.b.a<"s393ffi1mbk25b","KOMhh9FtwqZjlfLVe6i2emIexgDif0aBOEhHJ0jWJI0=",-3895165467829090355,3098117195429801568,-229885824204944451,7721883306843272530>()) {
                  case -390630851:
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
   public List<String> f() {
      if (this.C.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v8l9qtzupoq4","QFdzxE98SK/O8JlqYt4PWwWTJwzVy18LVYuzOLKInGg=",-8525704267707932057,-4790870372121264094,-2599075172169379017,8011725942140817268>()) {
            case -214598142:
               if (this.C.level != null) {
                  ArrayList var1 = new ArrayList();
                  if (this.a.af().isEmpty()) {
                     label60:
                     switch ((int)com.yiyiaddon.m.b.a<"s1aks3tvg6ezwf","yYPwMk3jscxVPNZUb+Ze2kDYctiDBIJQ5uMaLtnqI4w=",-7611568685751390189,-5493623836120087478,5773313445771555222,1093909285639811245>()) {
                        case 1557612448:
                           var1.add(
                              (String)com.yiyiaddon.m.b.a<"s3kxsjkc6zyh9b","07uPvE2mn+mmCkR1IWfV8CyXfnPhsD7X11xiKIKPR/yDJUKqd+LdJVKUCk/MRCt2MDx26A==",8019730070519991310,-7927863727993489595,-2686304573145269360,-7054048533741631392>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s3oomjxnq3we34","VEqrio7ysHONIb1qs/SIypBnnYMvoSMAygCxYbEEUdM=",5172101393068787165,8366532266181308373,-5546757091861525842,-7879978827473822112>()) {
                              case -178643415:
                                 break label60;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (!com.yiyiaddon.i.c.a.ft()) {
                     label55:
                     switch ((int)com.yiyiaddon.m.b.a<"s2pgps6qoz1zpj","8hve6hHiKGKh4esYmwrpNroDmjGNtjl98lL8uEemsB4=",6947868585205119718,-337368404631975781,5881132163847305576,7536414681432346108>()) {
                        case 1192028405:
                           var1.add(
                              (String)com.yiyiaddon.m.b.a<"s3ulb1k50z4be3","JfEQ8nsS/QGqTdlKXliEIqvVcUHo2YePgRnCvwVCJfK6Wm/qCFhOdKW38Sfmcv3G80ghrYVWSiiYNlNhxSwBLmDqpEk=",-4462934081935557299,-5045484964181405876,-7895670597300914248,-5732904048895497953>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s15x6h5yln26i3","yKzg9UflMPAzw0JgMs9Kr4Fd3z4evbSGQwjX1pxd6rI=",5408979519049288812,-8118686719883689063,3467063473483979299,-403994899780084125>()) {
                              case 604603818:
                                 break label55;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.a(Items.LECTERN) == 0) {
                     label50:
                     switch ((int)com.yiyiaddon.m.b.a<"s2hbm9jfg7yjub","tr7nnEmQVCDCn3MV5eBFprWFpcOFBER35Koro5AoGZs=",587385251709068201,-9114865582976245272,-5620213007282619000,-4439025403478430057>()) {
                        case -1048891278:
                           var1.add(
                              (String)com.yiyiaddon.m.b.a<"s21fcpt50mdz2v","Turl5Xcv0bSbQSyeloLgvytXULhKTNeH0zs/fVFYsPe6TziumCrvfMkC3VI6VYse1u0=",1986664869955286006,8712061030323984033,645495204095572892,-6109218771080732258>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s2n15gdsrihy8d","jdBIasW3NxTwOPwjf5uaNsr/W9L/D/NIqyAFjDwU8O8=",1202340852282789390,-7176667859642434714,7595685331913551695,2990860879720867622>()) {
                              case -823454209:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.a(Items.BOOK) == 0) {
                     label45:
                     switch ((int)com.yiyiaddon.m.b.a<"s29yqr3lj4i9hp","6lEjbfgf4iM4k+JRTlGlIhK97UM/Xu9kKB6nqSiVYAY=",-7173472822691244238,-6383037625308618489,8748114461593712289,-1508506315515365852>()) {
                        case 1679322169:
                           var1.add(
                              (String)com.yiyiaddon.m.b.a<"s1qvxmetx0syfw","j+Ou/WXpZt0rgWBuwm1ZpiXZ8xNz/MerJBqoGLKh99zVyzHZQLEEN+STcYivHBQ3",-7136972024972467619,6226627109753661156,-8501807778436578624,-7745836811798301551>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s1u6cdyzg0bwqm","6loLGOVWeY9lBDjifk8J0zBoXsEzF67FaWOwyjbDOwM=",-8138495353461501504,6414705776739832454,5519863034622885608,3010149320808710938>()) {
                              case 1585113640:
                                 break label45;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.a(Items.EMERALD) == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2oeurwniqu17b","xQUI+ftzDXqC4xeas1+aW13GuESHbRvyal0o+7B9V7o=",6293893425484719154,-1104811995624059313,-7441576838368673049,3970448020447277063>()) {
                        case -1371346694:
                           var1.add(
                              (String)com.yiyiaddon.m.b.a<"s1v501f47lo4m3","PHZq3UAOa+5K6Lh59Nml7BL0dF/HPFR0n95G1h0RfK7cmXwdLyhbFSvVD3C8hZfmqjkE3g==",4172304358517934435,-2149526544379878569,-1175861107380486886,5965286083267227502>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s2vbluzp6k9vxz","1Msrvsbs3PuHaurq9JHr5NTiu3HUzSMqjbYsjI8ln+k=",6376613814272872283,3620713639055552927,565132455526479542,-6986714877598178128>()) {
                              case -1036228898:
                                 return var1;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var1;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s59nykixnv401","zNOEFjnNPoMSnpVq5D0g3vT4I3685hjdgRoCotCyo6U=",-1454576205812176120,-1532913504547957835,-2552674922775965095,6190717816292124739>()) {
                     case 188830149:
                        return List.of();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return List.of();
      }
   }

   public int a(Item var1) {
      if (this.C.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sehywfhvytk4p","aXA3+dnqRPU9F2aUyh7L/jqiyxK7rGemmxrnUDmF40M=",7020312477363807671,7500992876993573125,-9220199867295971373,168342042711876378>()) {
            case -2138350810:
               return 0;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1e8ujuxaejw47","4kH5IPy4x1mDFB5l3isN1ughruq6H+8xUTOi3TKI0OU=",387038771705834518,324520557327091102,1270705325750746305,-4685386943440241229>()) {
            case -214838049:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2o7lph508n7a2","xWM/0dMsnjwMsXNDvALkUabtf4leS4LL34Sp4uLs58E=",-8854613562024847601,3588859856912550835,5442242431998592899,-3015557200349256408>()) {
                     case 453404553:
                        ItemStack var4 = this.C.player.getInventory().getItem(var3);
                        if (var4.is(var1)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s20d8l22qp1t63","VG7iHiAlqlGrumWOBgLTEOekGcSQX9t6sQYZ0TzozGM=",3250487135286766609,3534934767414091870,7690427297965827832,2047628510158300568>()) {
                              case -1316282237:
                                 var2 += var4.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"shduxgyva0jce","yOW3J49zrz4s5vL3sltw1gh7rXj2+pJ539Jz69Dt37E=",-5511766778200671290,108526443772375584,-5786849418629438611,-7196749867974717778>()) {
                                    case -1964196159:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"squcvm4f4lcye","EV4i0jpOA+fSJ3MmLO3/JylsVgPMl1AbDrVgK7FeNes=",5192320524733622121,-5159437933182070711,7817091398469518793,4813844630363774645>()) {
                           case -1661418352:
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
   }

   private void h(List<com.yiyiaddon.e.i.d.b> var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(
         (String)com.yiyiaddon.m.b.a<"s1htnhu8s2if93","8eV95sWDbaC/FzKk/DNmlCfjJ00T/Eavvpu9SQx9IJrnFJcZb7qNro6AdZbTD8EtvH5GKk0chK0CnMkfRgouLKJRpck=",-3986524839184955693,6429722237667108968,7719064132335759998,-5218248059458692564>()
      );
      StringBuilder var3 = new StringBuilder();
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3ce0swn2yjlvg","TMsZpbAr4vSoAAs0noQyvQ2IWSOnnCjnMaVs7aBz3Pg=",4336759065649191783,5279580360735144199,6037627462348298510,-3979457889823746499>()) {
         case -307291915:
            while (var4 < Math.min(3, var1.size())) {
               switch ((int)com.yiyiaddon.m.b.a<"s237k5q0atiy7h","Sl6GAkrkR4ZPQU1Rw13Vx5f4JiS+uGqX8RbVJj7BZX0=",-5966959241638386211,-8824068774244305835,6492994229411359553,-7272456668135303515>()) {
                  case 1635988006:
                     if (var4 > 0) {
                        label47:
                        switch ((int)com.yiyiaddon.m.b.a<"s2i7leya4n2o0p","VNZgVR5C/u2BH/zkNTmSnxbF/uzhXjVVVBuK1zgtRpc=",7755541112059055380,5540135860767249443,8861993960329044438,-592387887860123880>()) {
                           case -1526340484:
                              var3.append(
                                 (String)com.yiyiaddon.m.b.a<"s1htxahfsfhh9q","09eHB1K6Nb36YFknLAh2ZWUueP8989SWJ/VeD9QQ2+uvBA==",-4398369224684563568,3145379853130800510,-8082915320365301796,-1683865782849771006>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s19ik2vc9sncdy","9fBLtjRrcY8kZZkNppQRt/3m/NLXGwGCazElLWapopE=",-9020920169744889289,-6831689824277225531,-8767664197645052213,-582662168743479338>()) {
                                 case -107171725:
                                    break label47;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var3.append(i(((com.yiyiaddon.e.i.d.b)var1.get(var4)).m() + ((com.yiyiaddon.e.i.d.b)var1.get(var4)).ai()));
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1lszzilk8gbyd","Hx3N7bUIeqigECkhYaXObze935MGVzJiP6LgO7QIjiY=",-6380231659195902487,7997325172888888901,-3893878124501844050,-8191799749168447881>()) {
                        case -1374428413:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var1.size() > 3) {
               label39:
               switch ((int)com.yiyiaddon.m.b.a<"s21w1qqpxf2pto","o35zK78Irt/QXUMmdhlmMuZs/shrcZAJPUqDF2WucVI=",822433077834691173,1514222738922574421,3161314686926165495,8422606373062760149>()) {
                  case -1819779671:
                     var3.append(
                           (String)com.yiyiaddon.m.b.a<"s3e5ilhwczj0ki","ZViuttm0GfxXpyyJuTUU53SRcki6hGAV99KqKszOGzk+dfr9xkQ=",1907476552599522091,378146780210262886,-1984043431218480244,-729215566952029408>()
                        )
                        .append(N(String.valueOf(var1.size())))
                        .append(
                           (String)com.yiyiaddon.m.b.a<"s2gzi9x01nktbf","B1Le5QRZGbX9h1EYgIAmzp82pD3YVqGRoT+zHx6DkeSgumxf",-1732083647158900271,4989147684951394073,4260290912951549500,-1415789701197017821>()
                        );
                     switch ((int)com.yiyiaddon.m.b.a<"s2iihfg40qvq2w","5Yc8HDrFkxOL0nRGy5dnHnGPRxv0cq7ZVgUaQs7vICg=",-9124453023640248493,-1081778258176994454,-1417285040207843167,9033174341342826221>()) {
                        case -498873167:
                           break label39;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var2.append(
                  (String)com.yiyiaddon.m.b.a<"s3j6xqre4owdm1","AK7XJolDioH2xVkJzbNVo4GoBM0rg9VQn/yjnopADEbSGh3knWx9+TJ8JJUhhhaYIxfAAw==",-8980770755682593130,-5245490117857911876,8696319337443261689,3909607603208608505>()
               )
               .append(var3)
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2cnbtnotlg0vi","f+pSk6BU6MlK8CDivkT7YHfiADZFX1ca1aIbz7787wk=",-6199501981166340256,3328738364330892010,1269165761664347734,6852893246453875447>()
               );
            var2.append(
                  (String)com.yiyiaddon.m.b.a<"s2jbmalbzxuer2","CDjqSsYqTQ/UvMmVODh38nRFSVA/SJsGfUhXdtv7FJ7zCBHyJf1pkmyviOQK90ZnRvm6eQ==",-6844454479961017967,821771776001334688,8214104857192689059,-5334519759207539316>()
               )
               .append(N(this.a.fj + ""))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2cnbtnotlg0vi","f+pSk6BU6MlK8CDivkT7YHfiADZFX1ca1aIbz7787wk=",-6199501981166340256,3328738364330892010,1269165761664347734,6852893246453875447>()
               );
            var2.append(
                  (String)com.yiyiaddon.m.b.a<"s340x5jwwz1mcu","pRUqHNX/XDBP475x7yPMyI99g3B8tpPWJwleTf+8sRk9OeaDVIomkBmGjhfc/c52WWHBmA==",-8096959836043123465,-3231757596887347244,-5548921682756670279,-3823339448233937645>()
               )
               .append(N(this.a.eS + ""))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2cnbtnotlg0vi","f+pSk6BU6MlK8CDivkT7YHfiADZFX1ca1aIbz7787wk=",-6199501981166340256,3328738364330892010,1269165761664347734,6852893246453875447>()
               );
            StringBuilder var10000 = var2.append(
               (String)com.yiyiaddon.m.b.a<"s365yhddgk5312","c4nzGOClVUlxph+nmhqqcIZ4bohgNCnSzWrc1Ql9IvZlU5McBqt0STce3miTcB2/rdUwfg==",-2191394581231273146,-8152069587943600364,-6739230048517767937,3163954369585989322>()
            );
            String var10001;
            if (this.a.bC) {
               label32:
               switch ((int)com.yiyiaddon.m.b.a<"s2qiu1k57u55f8","F3YpLtD5aebr0cJzFfl6YMuxUMWv8wKrDMZxnmSsekU=",860886684160446823,-5479497584335105639,4369322393500391172,1357004101388555895>()) {
                  case 755638481:
                     var10001 = (String)com.yiyiaddon.m.b.a<"s1ohpfbs5oucpn","30aNihkqPqTHynZ9CEVda9UXmM/sV+4PfSiynTjvHaCo+WeVY0U=",-4179342384964663611,-3236065463898766225,-5637397824724094718,3418709659786474022>();
                     switch ((int)com.yiyiaddon.m.b.a<"sy2iw5592jl9g","U0em9C7LIyWjzg7Gah9DaOz3RXv1IW+eDwLALw6P6Kk=",-6225642130812734691,8457176852649782944,-269325637560627891,-7592786491311771815>()) {
                        case -787806043:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = (String)com.yiyiaddon.m.b.a<"s3ohwgimd13mfl","bev4t+0Idea428nYUi3DRUn6ZzAadPUar+diTatXlmg2kB3Ht4w=",-9220214673974456460,-4257261339080782494,-1615018913502970136,-3962345235040321903>();
               switch ((int)com.yiyiaddon.m.b.a<"s2dyfefrkjqkza","To+sFvSLK8lzeWfsmZBdw9BYuEeTasULsf410hBNmC0=",-1326955432711836028,-8821649521327875135,4505626656147721718,4715436963060352997>()) {
                  case 1402376077:
                     break;
                  default:
                     throw null;
               }
            }

            var10000.append(j(var10001))
               .append(
                  (String)com.yiyiaddon.m.b.a<"s2cnbtnotlg0vi","f+pSk6BU6MlK8CDivkT7YHfiADZFX1ca1aIbz7787wk=",-6199501981166340256,3328738364330892010,1269165761664347734,6852893246453875447>()
               );
            this.X(var2.toString());
            return;
         default:
            throw null;
      }
   }

   private static String i(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2e3se8wg13ydn","DbSHRWx6vRXRiraRcToRGidpslVK+oTcJDSZ6WKHlA0=",-7920560429530382215,7321008060060403747,8958017913750154013,-2500131629566077475>()) {
            case -535055063:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1mfet9lj0i4g","Kx8Iqdgy0dKbpLjCGgGK8927aKAg8mblixSwzQ==",6388453887154337050,-5054847796621052733,3804980179298800340,4384104664884320356>();
               switch ((int)com.yiyiaddon.m.b.a<"s1v6gbis3ohuwf","DmS9vU6wPVS6vXZu0IZa5E/NPK+oYCT5SLnOBmbfjUo=",8309365364417322587,1102347082176155810,2883894563341998431,2209418613249637882>()) {
                  case 1683151206:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s368o4uz8qaxkg","r3U9Tr+buDtcsQsEFm04L4shX/9hTL5iaXJ/b4lbKtY=",-169478017482259565,9190204588274915203,4883758360066118516,8237380006317857198>()) {
            case 1206213221:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   private static String N(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f51ty6kosfha","2fkKyejwn50QmTWT4Nunr8KbZGqm/tFS4sG/u59y/9M=",-6604347313790678552,-3340402087966608463,-5518826222784739841,5626550710727100421>()) {
            case -1705521648:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1mfet9lj0i4g","Kx8Iqdgy0dKbpLjCGgGK8927aKAg8mblixSwzQ==",6388453887154337050,-5054847796621052733,3804980179298800340,4384104664884320356>();
               switch ((int)com.yiyiaddon.m.b.a<"s3subgyts25k30","PgpGw1lNrRCjND0l2dHbG9WKQ7Z5n3uFrzvnBCuLd5k=",5732594104303984037,-3876444867699108274,-4960836319648755894,-4008279865683127792>()) {
                  case -190606738:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"swllh3bx8y6le","s5lWFKpaT6QlAjEFH61nRBhPubpNmRwT0hleZql7joM=",-7244832451996422801,-2904182760813949599,3250394399044962526,4943453428943941282>()) {
            case 1734605830:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   private static String j(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fh4s7n3kdtn5","tceRlUAPCKB8P//tAA/ljwFslBnuV0jx02KNyReNY+Q=",-1647214037260393474,1597246731804448065,4009885120133922466,-7223364853963457357>()) {
            case 1582960051:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1mfet9lj0i4g","Kx8Iqdgy0dKbpLjCGgGK8927aKAg8mblixSwzQ==",6388453887154337050,-5054847796621052733,3804980179298800340,4384104664884320356>();
               switch ((int)com.yiyiaddon.m.b.a<"s3cyhaycsio6nc","+wVCluJVpThDxMlo6906ZFsoUMCq22LXBELeY+oxD1U=",-4404620680743579559,-3456928056148291011,6770806422987064623,8931040079707448887>()) {
                  case 883294788:
                     return var10000 + "";
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"smj5x682lpyev","ziang31z1Qw7eYul5Zyt7UxzB9L7TCT0BQVSWdUwCqE=",329833601570323443,2977098311138650015,5122059352667239155,378833409234383555>()) {
            case 2141566697:
               return var0 + "";
            default:
               throw null;
         }
      }
   }

   private List<com.yiyiaddon.e.i.d.b> ad() {
      if (this.C.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yaus9phls1na","Kr8GsHwCzysLHdd7Mu8M2TySAS4Rv5suRZGI9anEd1s=",4377257916619999532,3851242100655689060,-8721818892402942359,9040480199590690940>()) {
            case -1242610552:
               return List.of();
            default:
               throw null;
         }
      } else {
         Registry var1 = this.C.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
         ArrayList var2 = new ArrayList();
         Iterator var3 = this.a.af().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2cth8bpydqei4","SMDPXTSB8mVM4F/GKpvzQIS8aLndT3V4kgdlBqce+zk=",399579932063427849,-2260722350862053039,5878622787812919832,-8792618726908874482>()) {
            case 1004897638:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3soimdz1ucbyg","PYiAH/yfcjJP7UnLLtaLPXCDskOr9wk1BwmHgNHkR6o=",140667831296338864,-2624728421443644155,-7147478272705505217,-6103936713863914809>()) {
                     case -1927302212:
                        String var4 = (String)var3.next();
                        Identifier var5 = Identifier.tryParse(var4);
                        if (var5 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sx7hiswblyfkx","u+3SI8POTZS2eDoQzZWro4l8o3wvQM7aZmbm96b2M+I=",-8740756778418883726,-9020904983322913421,-4672065500599448556,-5643988899988951736>()) {
                              case -643653663:
                                 return List.of();
                              default:
                                 throw null;
                           }
                        }

                        Optional var6 = var1.get(ResourceKey.create(Registries.ENCHANTMENT, var5));
                        if (var6.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3dotsl4igay8l","P/RlRVfl/iwdoyDcdLl0dMVz3TuVO6o+35dYjy5sU/Y=",3244099307714911987,6427986563672922196,1825793925631133411,-6886508028890951160>()) {
                              case -1321600809:
                                 return List.of();
                              default:
                                 throw null;
                           }
                        }

                        Enchantment var7 = (Enchantment)((Reference)var6.get()).value();
                        var2.add(
                           com.yiyiaddon.e.i.d.b.a(
                              var5.toString(),
                              var7.description().getString(),
                              (String)com.yiyiaddon.m.b.a<"s136h4v8mmxarp","odgXT7ALoqICXlwcUx0c3zD72igOdfL1u2xVZ+2Jn2QhhWbdJzH95iPcqjL4viZyt5ngXXoEWiByLW5fPWe5LzvMvrcVGu7s9Qb2Qw==",909693652871772721,-524977280936038703,-8291668068763242222,-7189755172649352332>(),
                              var7.getMaxLevel()
                           )
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2f7jt71gpbtnk","60Fw+C/m92a+RcDIPz+kLcIS7CtjbD6aGRacT37qHh4=",-4015239342243842079,4834618826382920789,-1971823173286378161,-4063225482359265691>()) {
                           case 1963404208:
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
   }

   private void X(String var1) {
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s3qtjliy48wykf","MI4iBMf+lWv7dJtP0JDxW3UGpxx7mRq8fN69a4QQGSM7lm04wt8599GD",2290159842115387471,8467887827277638254,-3412899176770718725,6201253149720373966>(),
         var1
      );
      this.v(var1);
   }

   private void v(String var1) {
      if (var1 != null) {
         String var2 = var1.split(
            (String)com.yiyiaddon.m.b.a<"saabn1uo7dnif","3ltG0xGnGMwwcIik4ycUvSkANJT2jrsuOMfm+B/s",-7016314419169831543,-5079713953202942908,-7701992118430566618,6082527216259899644>(),
            2
         )[0];
         String var3 = var2.replace(
               com.yiyiaddon.d.c.e(
                  (String)com.yiyiaddon.m.b.a<"s3qtjliy48wykf","MI4iBMf+lWv7dJtP0JDxW3UGpxx7mRq8fN69a4QQGSM7lm04wt8599GD",2290159842115387471,8467887827277638254,-3412899176770718725,6201253149720373966>()
               ),
               (String)com.yiyiaddon.m.b.a<"s1mfet9lj0i4g","Kx8Iqdgy0dKbpLjCGgGK8927aKAg8mblixSwzQ==",6388453887154337050,-5054847796621052733,3804980179298800340,4384104664884320356>()
            )
            .strip();
         synchronized (this.c) {
            while (this.c.size() >= 80) {
               this.c.pollFirst();
            }

            this.c.addFirst(var3);
         }
      }
   }

   private final class a implements com.yiyiaddon.e.i.f.c {
      private static final long o = 2000L;
      private String lm;
      private long p;
      private String bR = (String)com.yiyiaddon.m.b.a<"s3svckrwhlw90h","hu/QedimRaoJbUL9Lpocg/AnCbvpmhFYasLLMw==",-7748289590578038449,6453845757076900898,-5425381420028492483,403222028990365803>();

      @Override
      public void a(com.yiyiaddon.e.i.b.a var1, f var2, p var3) {
         if (a.this.a.as) {
            label13:
            switch ((int)com.yiyiaddon.m.b.a<"s2lt5soq6r40g5","A2Wm+R9EyQOqBtCE7UojzUdC/A7en6VIW98GOFnz724=",1823268414796153899,-6979773477170770800,4906631325469805972,5179213505912149264>()) {
               case 1013471513:
                  this.Y(this.a(var1) + this.a(var3));
                  switch ((int)com.yiyiaddon.m.b.a<"s2h1qogj1efz8w","ogHMxok1tdrJhSAcKXPUpjp/ngfzjUDqZMBp3T5KfPk=",-5267982484140923808,109876332879569029,-6230357143393909420,-3149279767279122550>()) {
                     case 449977578:
                        break label13;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(var1);
      }

      @Override
      public void K(String var1) {
         if (a.this.a.bE) {
            switch ((int)com.yiyiaddon.m.b.a<"s23aoqancqbpxr","rGPlq5srjylFXWdSN25DoDvOJBfu9MqAs4BhDvmstjw=",-6649443586150292534,2200035141078881880,6244159958399166953,-8336400465787618149>()) {
               case 579851392:
                  this.Y(var1 + "");
                  switch ((int)com.yiyiaddon.m.b.a<"s42yy5yc1nv7d","/RcceU/A1FLF7AdpPhTWLQ/NUYHQ9+YCW5A8IZ17/CI=",7048208798636714049,1908148931286021730,-1929364261559310402,4969383875472510924>()) {
                     case -842823708:
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
      public void D(String var1) {
         if (a.this.a.as) {
            switch ((int)com.yiyiaddon.m.b.a<"ssz2piiozdw3u","jqLt+9K2OO2xLRkMPsKHM+IMIdmmnx13qKY8M+VOPP4=",-6277012740764308805,-3088302758101532412,-7231967474980952629,5859505309424012909>()) {
               case -1415189949:
                  this.Y(var1 + "");
                  switch ((int)com.yiyiaddon.m.b.a<"sagxlazqe4wjn","Dz2el8FwxgxnQYjOSYQiDWEOdBeotiGohyUIsgelU7U=",1433921082484777875,8020918435480632971,3628331263218785732,-4390467532836907226>()) {
                     case -614565226:
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
      public void b(String var1) {
         long var2 = System.currentTimeMillis();
         if (var1.equals(this.lm)) {
            switch ((int)com.yiyiaddon.m.b.a<"s1p2ulp9suuxll","6rCE1oThxtJ/GgVDVkPMl/OHqT9dlA2X1iz85TU21bE=",754582749595900301,-3669208670616354842,6469080621904075297,-3749023831851677666>()) {
               case 222619713:
                  if (var2 - this.p < 2000L) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2b8huwqfzln83","FBiWL093bGMtbwcWYavpMle4zN3kzSiQtwvrZXj1dRM=",6394001725388604631,-3804097518867651736,-4248406523564581048,-3168994472649629907>()) {
                        case -1118874577:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         this.lm = var1;
         this.p = var2;
         this.Y(var1 + "");
      }

      private void a(com.yiyiaddon.e.i.b.a var1) {
         if (!a.this.a.bE) {
            switch ((int)com.yiyiaddon.m.b.a<"s3d5ij7mknzj69","BPJiijiQA4PsiOZx8PoIGwjQt9WZrRSctY8Pz3WlUPs=",6139391050000377809,7212326882503435093,5203666609145695122,6152232355098642396>()) {
               case 1581385648:
                  return;
               default:
                  throw null;
            }
         } else {
            String var10000;
            label58:
            switch (var1) {
               case SEARCH_VILLAGER:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s137elzz97rrir","Adp+09hKpHzn5b/i/kKXI0HGMkLDTwc9NNk6CxqsXNcdIoif9nXS3z/tBkQUwA9bjyklsyYF",-3533335439585347931,-7020119556955563749,8654707848482160930,8891007843046537960>();
                  switch ((int)com.yiyiaddon.m.b.a<"s23d1smc57ei3y","ipvIuNLBOV5/sZ9cXsQ3m0zUfcrks14zVEezhocr6bE=",2306237024701757957,5254022213023422549,-5215616104412243365,2988263407003612334>()) {
                     case -686184801:
                        break label58;
                     default:
                        throw null;
                  }
               case FIND_LECTERN_POSITION:
                  var10000 = (String)com.yiyiaddon.m.b.a<"spcr98di47jt5","bIujqsE+6wlgCEe1rko2WJD1SzZQGMqvqgHH6SSddUymGjMXLM4F1NDORCZ5wVS6u44=",-3806363475630229649,2595252354911711857,8155404955749374738,8484560525522590730>();
                  switch ((int)com.yiyiaddon.m.b.a<"sdmcgt53m70bp","BzLLZXUsuUCY2I5wu+LJCmnb8Zv6lzxGKkRMBXDL6pM=",-489423329152949707,-7833552538609118332,-4048183085689820240,-3604391825195302245>()) {
                     case -148139400:
                        break label58;
                     default:
                        throw null;
                  }
               case BREAK_OBSTACLE:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s1kyb9erejtyjc","rlg3plKgigL19K7qEawxLI32PRxyzBus+dIxSlvLAjShm20vFRSvMFFc04uTD+iVMnLTjiEf",2759687393605883163,6175035128195841558,3432123106709126794,-1052777594611337327>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3ub86onb1ynn4","uXTpZN+Xoqkoh7o1rYWY2bBCLGptlXq//5P+t+32Kt4=",-5946671932230672821,-3553577708239871875,-3413388234589032600,-3623354764531061854>()) {
                     case -87433252:
                        break label58;
                     default:
                        throw null;
                  }
               case PLACE_LECTERN:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s384hli6cp1ccy","kyRcsguW4rhY7AVnVL7ez4FnwjQ4YD5XhTm87q/QVQQMVYi8+OtxbD2LOJFNGkBPvCQ=",5703509973278267294,-8363227423472009763,-8798051168842052831,-5857614174465997699>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2vwy54lq4raj3","X604o7ayF7h0KRQrtchtvYi7pDFRCVz9WySdY4PpVp4=",7117658799503260064,-7302215239860644833,-7195335035056615119,-3799926500761099486>()) {
                     case 509270255:
                        break label58;
                     default:
                        throw null;
                  }
               case WAIT_PROFESSION:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s3oci7xqddvtjn","g/YMn5lj3Yx6/6aCOpV2YuqTMw2MDZ2wTeN8DV2l0kW69QQ8SSYQ7HFVFgzU/lLN60nkXAYwKULvXyXW",4239993506687336038,2503166722231397767,-6827435138702458183,-2685258870821006324>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3a4nxekc303vk","AEUGPAHuw5QxEiIZuT0bQAwC7+WA43q/cr9do324N6g=",9176584109888737084,7550223444440920428,-4740593346698535548,754974539239904150>()) {
                     case 1324563673:
                        break label58;
                     default:
                        throw null;
                  }
               case BREAK_LECTERN:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s24uhuqownmenb","aB/usLqRzpZKuXm4wlOgIe47B5SV3Uf9TwlUJjlHSwzRKJxga9Zxa1a4gaQKzNbYwHA=",-444206099508707505,-3109969479975875515,-431278742553054505,5385932911919596167>();
                  switch ((int)com.yiyiaddon.m.b.a<"sntcmvtvkpq0t","G7MWKioAT1IfClAstA369ZlZWsDtL5esJW3JCy4qzYk=",-3696141920216966150,1278682484838614339,-7345080408070798874,5235297937996327977>()) {
                     case -1132874658:
                        break label58;
                     default:
                        throw null;
                  }
               case RESET:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s1hm9i9nyh5lq3","N8js66UZToAkhpteBRORV2XMYYEDpzE0zNYgtJB7mNr9bj6YYMHgGiT7MXZR8WdJ/Jsb8QIolCpEyroZ8RtM0oB82Jc=",4319989862965432698,2751178498249853908,-8945181896910239706,-9022359407185447616>();
                  switch ((int)com.yiyiaddon.m.b.a<"s10sc040ece1nh","zy7MPo25+GGUnhnoVMBbZjvy4r3ZS8UBfvN4+ARMxhQ=",1497836168460241723,6328051275797198558,-2358312935962168518,8172275811191887055>()) {
                     case -596150154:
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2bjjwh3p00mo8","4vbWEOCYPm0CSUYnyzv7ZB3L8aRWTpqFaj3zAK/TRLI=",7211016661736630423,4873989868261353208,4425765261767641320,-6590488925917845228>()) {
                     case 1418000788:
                        break;
                     default:
                        throw null;
                  }
            }

            String var2 = var10000;
            if (var2 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3rhjlut2medg4","00LXAXZXuRySQKgCQoJ138NQhtj9jpAgMlcZ+FM33Mg=",2859579613447673119,5897267026835827584,-4563308780220739292,-7400588759129091707>()) {
                  case 964130649:
                     return;
                  default:
                     throw null;
               }
            } else if (var2.equals(this.bR)) {
               switch ((int)com.yiyiaddon.m.b.a<"s3knfloc7eq7o5","CgLk/Nrlp41W/4JqgUrBWvcbWQMRCcZ9YFmz1D64xxo=",-8366521369079676615,1150477855474283658,-7019466071818201201,569113459944862441>()) {
                  case 900253646:
                     return;
                  default:
                     throw null;
               }
            } else {
               this.bR = var2;
               this.Y(var2);
            }
         }
      }

      private void Y(String var1) {
         String var2 = this.aj(var1);
         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s26fcp19ydb083","l3aA5eQSouTZM++yogqz6tA91rm9G81B7MfB4AzpM+9YnrKazNivUnQH",8443096208952936376,-5871431596857848268,-4282184226036832831,-1005298787114069235>(),
            var2
         );
         a.this.v(var2);
      }

      private String aj(String var1) {
         return var1.replaceAll(
            (String)com.yiyiaddon.m.b.a<"s3av17t8t91dz8","HQ2oL1McRrW1UoAoERGE3XUE9nKy13VhPju3vRYi1CsIbGJ7DBUVQLhww2KRwtO4DK53AGbIyQiiQbWlQig6Al6b+aTgz1RHEYpPKwNYHm+ni8g6YKb+k6DK",1056058271764650443,8222556046711419159,-7944184726092956675,-8442619866000977619>(),
            (String)com.yiyiaddon.m.b.a<"s3svckrwhlw90h","hu/QedimRaoJbUL9Lpocg/AnCbvpmhFYasLLMw==",-7748289590578038449,6453845757076900898,-5425381420028492483,403222028990365803>()
         );
      }

      private String a(com.yiyiaddon.e.i.b.a var1) {
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s25qrzurklfbtt","fSHhcXmC+tsnkcIN8vHqW7rM87YCbdP3KvDVed3rGTA=",-1938928122514828374,710099753115124726,-647731464085105964,8793032587321412000>()) {
               case -438622420:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"svh92wn7o377i","zKFLhRXBckbG56MBlyfOZ9GxMMcAigUnn6/FSP7OF+ZAM0jl",-8107960979034407557,3178268894813661026,-6238653696662702376,-3534076642628904323>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3f9pczbdtdaww","jVRIpPSrMBPmH43Po702RdZHjzo9Gtz+h2g9YoQ5Eq8=",-2077485192962696762,-5606761977277470752,6058918995437015570,-4878377388653942418>()) {
                     case -941526891:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var2 = var1.m();
            switch ((int)com.yiyiaddon.m.b.a<"sz1f1d5b0aqol","tBftJdQ9sRXQaPdCm/s6B6gX22wfd6hWzuk2hVhYIi8=",-7657269787129341031,4813579910463692786,-6153107621759552559,5908972793867498559>()) {
               case 341058159:
                  return var2;
               default:
                  throw null;
            }
         }
      }

      private String a(p var1) {
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3bit4bpd05lja","WJdT+ym3ePA7TLY0Q0HsMhiaQJAJTIsS+iQ0QZ3xbAQ=",-1848071919440913770,6435143540906276601,-678742889355156191,8496038852065807771>()) {
               case -575287727:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"svh92wn7o377i","zKFLhRXBckbG56MBlyfOZ9GxMMcAigUnn6/FSP7OF+ZAM0jl",-8107960979034407557,3178268894813661026,-6238653696662702376,-3534076642628904323>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3lk0ylizwas6z","OOuVszHmG2a09zM2oYzHcLy/Yz0mkgszHu6YgggqSo0=",8914204630428024177,3831432370386210855,3660455359558263309,-4099966199841529638>()) {
                     case -2049093971:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var2 = var1.m();
            switch ((int)com.yiyiaddon.m.b.a<"s9tps7m1g16ar","zq4SpBlh7rE7cyNdK3NSldjaVr4XLSuyd8ysnusHXK8=",-8988068398660437645,4932848402702217236,3425440551464398262,4109541451181668639>()) {
               case -102383100:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   private final class b implements com.yiyiaddon.e.i.f.e {
      @Override
      public void a(com.yiyiaddon.e.i.f.d var1) {
         if (var1 != com.yiyiaddon.e.i.f.d.SUCCESS) {
            switch ((int)com.yiyiaddon.m.b.a<"s1fc82swf483qw","9smH8xLaJ4LhSqqnBer5K2mi+qrfr2fy2inNC/jnIms=",4817207088803533517,-1491728434571504211,-5077484090558669477,8020181660469911811>()) {
               case -343272440:
                  return;
               default:
                  throw null;
            }
         } else if (!a.this.a.bD) {
            switch ((int)com.yiyiaddon.m.b.a<"s1d5rqv7bd5omi","/r1Q3Ci+3PNCHwr+jYFDYjAalVELyVDSsDXL6gCSNEU=",4964359424474062000,-1860840585392563330,389879518994689227,6280091700216778771>()) {
               case 508971854:
                  return;
               default:
                  throw null;
            }
         } else if (a.this.C.player == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1h7bh82bblrn1","CvJdnfzqIDKjOGiJ+F+u5RcdNsWghk0B8gARNozBsUM=",-5106762365084259445,-4971157635887619953,5230872030723697886,5577867627118263358>()) {
               case 1278288139:
                  return;
               default:
                  throw null;
            }
         } else {
            a.this.C.player.playSound(this.a(a.this.a.a), 1.0F, 1.0F);
         }
      }

      private SoundEvent a(com.yiyiaddon.e.i.a.c var1) {
         switch (var1) {
            case BELL:
               SoundEvent var22 = SoundEvents.BELL_BLOCK;
               switch ((int)com.yiyiaddon.m.b.a<"sh80o865oyqg1","fKKLFYReY0bKXrSb86EeVlCXAZPL3MDJYsk/bAx52F4=",6723364033125572393,8372699294474728399,-4961102629687818804,-1993403783268896058>()) {
                  case 1266893728:
                     return var22;
                  default:
                     throw null;
               }
            case ATTACK_CRIT:
               SoundEvent var21 = SoundEvents.PLAYER_ATTACK_CRIT;
               switch ((int)com.yiyiaddon.m.b.a<"s3m5ar580dg98m","XfEY5qK3d7CuixsXf+owM6zwRpDNdPb+EG7rI9aY/Q8=",-1098711074184933274,-8199946271380680207,-8026852699830428334,-7503918100675794420>()) {
                  case 1917062163:
                     return var21;
                  default:
                     throw null;
               }
            case CAT:
               SoundEvent var20 = SoundEvents.CAT_AMBIENT_BABY.value();
               switch ((int)com.yiyiaddon.m.b.a<"s1cvomxvax355f","c1/kbe1dm/8mUlLGPOp7O176t2TaH2EozToiSvV2L7s=",-6980458246489505598,-5662594048545227420,-773469715099109558,3963837507467450867>()) {
                  case 1477704981:
                     return var20;
                  default:
                     throw null;
               }
            case THUNDER:
               SoundEvent var19 = SoundEvents.LIGHTNING_BOLT_THUNDER;
               switch ((int)com.yiyiaddon.m.b.a<"s2awfr3qnp82ko","coW4n57C7dWaFAII6vJLTdx6J2iAgv5Ezs4Q1lRIXDM=",4532040539670792764,6484665447734955612,1220014418075092601,8304227453289395149>()) {
                  case 780396713:
                     return var19;
                  default:
                     throw null;
               }
            case EXPERIENCE_ORB:
               SoundEvent var18 = SoundEvents.EXPERIENCE_ORB_PICKUP;
               switch ((int)com.yiyiaddon.m.b.a<"s1xvh45n54npdi","H7LfAjqdUO7TmgFBJUdGspTS3PjFBrFxkfx378SyH8g=",-3152652259722086995,-2841324829583637209,-2152149539303666304,7061693900023197733>()) {
                  case 1875391446:
                     return var18;
                  default:
                     throw null;
               }
            case CHALLENGE_COMPLETE:
               SoundEvent var17 = SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
               switch ((int)com.yiyiaddon.m.b.a<"s1ywrmqpqc102y","atwm4IeNX+LN9YUcJJQtceIe6+zZ7oy8FnSS5YZz/q0=",-7853931170117502633,-4622475884821104730,3694861392111617065,7411053581818809071>()) {
                  case -1742142087:
                     return var17;
                  default:
                     throw null;
               }
            case PLAYER_LEVELUP:
               SoundEvent var16 = SoundEvents.PLAYER_LEVELUP;
               switch ((int)com.yiyiaddon.m.b.a<"s1k8tswfjz2r8o","8SljSGEG4WRwOC5Te8LXNZnmt7H6M6gAPOAYNQbZVE8=",-6617027208190198769,-8205373239531938673,-5258351903572572183,2861715415825460033>()) {
                  case -372081717:
                     return var16;
                  default:
                     throw null;
               }
            case NOTE_PLING:
               SoundEvent var15 = SoundEvents.NOTE_BLOCK_PLING.value();
               switch ((int)com.yiyiaddon.m.b.a<"slb1mu5804iqk","Y8I//VRNph01Hu3pKxx8/vIH1WaFXx9PxFdTmCqZfQo=",-93992092353580307,-3906969289594371739,-2374211200926730366,7154204443610601110>()) {
                  case -1204866015:
                     return var15;
                  default:
                     throw null;
               }
            case CHEST_OPEN:
               SoundEvent var14 = SoundEvents.CHEST_OPEN;
               switch ((int)com.yiyiaddon.m.b.a<"s3hrd5vylfgy6a","L6qAeTlMTjArd0/r6OZMAYrnu15oRkbrjmKPhG/FxDk=",-4681655797454167096,-8612543450783250355,1295117006623362990,-3324185291250419637>()) {
                  case 2025843911:
                     return var14;
                  default:
                     throw null;
               }
            case FIREWORK_BLAST:
               SoundEvent var13 = SoundEvents.FIREWORK_ROCKET_BLAST;
               switch ((int)com.yiyiaddon.m.b.a<"s23nue93cm4ox2","mERCdzgJpDwtlvb+AkJwqjB6i/a/V+9BC+40x5J/slU=",3342706198818861274,-5422690226281181399,-3635518432778407455,-6008708351972569365>()) {
                  case -907933728:
                     return var13;
                  default:
                     throw null;
               }
            case VILLAGER_CELEBRATE:
               SoundEvent var12 = SoundEvents.VILLAGER_CELEBRATE;
               switch ((int)com.yiyiaddon.m.b.a<"s3udsvo543cb7q","I1Uu4XMKVhYcKzD8kTyB8/DD3GboyJvAIRL5dszu+P4=",3080657030958752472,729067750388840819,2869891363800107833,-4003858564269407859>()) {
                  case -252290359:
                     return var12;
                  default:
                     throw null;
               }
            case ZOMBIE_VILLAGER_CURE:
               SoundEvent var11 = SoundEvents.ZOMBIE_VILLAGER_CURE;
               switch ((int)com.yiyiaddon.m.b.a<"s2w660bpnh4f0x","ZeBtfrWBfybIpiQRY0eh5KsLqy3DaE8gkL5s0bK1oWk=",-2708886213131294360,-5085047139760519478,-3029378063697361823,-8046366608444186407>()) {
                  case -1761972993:
                     return var11;
                  default:
                     throw null;
               }
            case GOAT_SCREAM:
               SoundEvent var10 = SoundEvents.GOAT_SCREAMING_AMBIENT;
               switch ((int)com.yiyiaddon.m.b.a<"sth96wykvxqjg","NwWSMgMT7yhc/DZzdayYBZbwxTyfbeZT0VoMbODmqo0=",-3245579694698255825,1164851297991715918,-4315841958475193156,-9040364612682412991>()) {
                  case 153316281:
                     return var10;
                  default:
                     throw null;
               }
            case GHAST_SCREAM:
               SoundEvent var9 = SoundEvents.GHAST_SCREAM;
               switch ((int)com.yiyiaddon.m.b.a<"s1lancmdqyxyt7","nLJFOejayaWJQgRqcFygwt8F2wh/ArMrT6tAoKzec88=",-6220528346802415455,-2929476172331549755,-1009926205259096700,-574900038713939941>()) {
                  case -1297850146:
                     return var9;
                  default:
                     throw null;
               }
            case ALLAY_AMBIENT:
               SoundEvent var8 = SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
               switch ((int)com.yiyiaddon.m.b.a<"s144wodc24szzq","1XAH+pfqODo62Amr0BDZopQ/twzH+vAGvwxFKIt/JJM=",6076254209670665034,7611760463625810289,4761153534194574944,3192213453593161038>()) {
                  case 489765062:
                     return var8;
                  default:
                     throw null;
               }
            case ENCHANTMENT_TABLE:
               SoundEvent var7 = SoundEvents.ENCHANTMENT_TABLE_USE;
               switch ((int)com.yiyiaddon.m.b.a<"s3ixeoglso7wkc","t8fks+xRDgC0PfWalvXvxMN3DWabebaf1RQVU2dVqCQ=",-2733838619268924387,-5208478343316547074,-5484155195394394431,-1616155738796445932>()) {
                  case 293811892:
                     return var7;
                  default:
                     throw null;
               }
            case TRIDENT_THUNDER:
               SoundEvent var6 = SoundEvents.TRIDENT_THUNDER.value();
               switch ((int)com.yiyiaddon.m.b.a<"s1givnaritgl03","qRZDLSSll4yVM9Xyp5euSeOZBgM/HQ0VP/9mmXZ/M3U=",-6333621624270052922,-6206675537460376936,-8759009506737744002,-3955124763522612249>()) {
                  case 1347120086:
                     return var6;
                  default:
                     throw null;
               }
            case PANDA_SNEEZE:
               SoundEvent var5 = SoundEvents.PANDA_SNEEZE;
               switch ((int)com.yiyiaddon.m.b.a<"s28x3gt0rzz2ek","o8HVX6w0oFsWrv4ruA4k52csC+uAVARVVovFD0mN1Ow=",-7172313180783051850,7980066040664640015,-1184132241125532126,7575978395480958957>()) {
                  case -1855331592:
                     return var5;
                  default:
                     throw null;
               }
            case WARDEN_ROAR:
               SoundEvent var4 = SoundEvents.WARDEN_ROAR;
               switch ((int)com.yiyiaddon.m.b.a<"s2mha6r6lagrwv","mnXW5OdvfttH8H7djmDuKnjI7n8KqGNoY3X6AN4bhv8=",2085016920488189429,7174414731708157034,1121595487273973188,-1067987715409384036>()) {
                  case 1906261945:
                     return var4;
                  default:
                     throw null;
               }
            case DRAGON_GROWL:
               SoundEvent var3 = SoundEvents.ENDER_DRAGON_GROWL;
               switch ((int)com.yiyiaddon.m.b.a<"s3k5pd1spzeymc","zY9h8d2q5T1a4ZCT7cTr//JRo0APL9wAFaIOnFrjOLI=",4541605216565964822,-1732578549667576093,-2561641204376229351,1279487347317187159>()) {
                  case -130641946:
                     return var3;
                  default:
                     throw null;
               }
            case END_PORTAL:
               SoundEvent var2 = SoundEvents.END_PORTAL_SPAWN;
               switch ((int)com.yiyiaddon.m.b.a<"s3rqwje3hi3q3c","zj4W7tYebPTe5riHbhtLQudqXnTH3uqyWo6dibxYs5I=",3639957374452752249,5840630708813182346,742783484313961796,-117944644226362280>()) {
                  case 416413640:
                     return var2;
                  default:
                     throw null;
               }
            case ELDER_GUARDIAN_CURSE:
               SoundEvent var10000 = SoundEvents.ELDER_GUARDIAN_CURSE;
               switch ((int)com.yiyiaddon.m.b.a<"s63xxxnebfnt9","Yw1jXT7tBnYLtXeydAHQK9+JcT8/6HjZ3TKOvSnByIM=",-6485242570665642985,-806237684528148286,3194508845298902842,-4049067323480703163>()) {
                  case -485206875:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }
}
