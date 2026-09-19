package com.yiyiaddon.e.c.j;

import com.yiyiaddon.l.c.g;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class c {
   private c() {
   }

   public static List<g.b> a(com.yiyiaddon.e.c.a var0) {
      return List.of(
         a(var0),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1930bc4mfhoak","3fuUIbiiN4TUXAFWTHFY0dcSpBXMZlv41wBAbgHR580Zr65g",7620880627578824707,3903361219103005857,-5745510354780930759,-7673272800790810141>(),
            com.yiyiaddon.e.c.d.g.SINGLE_STORAGE,
            (String)com.yiyiaddon.m.b.a<"s3nl4lwbjwgka1","XxkKWLXy+67FxWROn7LocH7SH63WhaIA/fYJUTIJ6FE=",-386258592888704503,6633715095172043906,7949423482741686583,-3242819614363701387>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s38ypcxsqnignf","ELsP2EOZ0qgVe5POGIwooc0VJWN+BJudJac1EL4/e8ZuQLA+",-4012466509601949387,-4718554451124161387,-9183844390337695837,5815394326102469790>(),
            com.yiyiaddon.e.c.d.g.MULTI_STORAGE,
            (String)com.yiyiaddon.m.b.a<"s304kpgn02neat","lEvFr6fhfSyHgSeNthJbYjSv9E9vqN0aCqdfZzMwNyA=",-3400805165822491388,1060955672600811876,-94847456186216142,-761049291391670683>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s2mr9l5i22ilan","IcIWu6A7hza2C7SFZHZHfo/twysf4OdhHHJ6t0qrKWNaBCZ7ifA=",-7356562589312535826,-4548636408022328099,-7737820595196070972,4848353894441861484>(),
            com.yiyiaddon.e.c.d.g.SEED_STORAGE,
            (String)com.yiyiaddon.m.b.a<"s3oxvzzbnkydcy","apXbDuycVZf7aATY4SfZh+IpZUcNrikFkX0EcJXibN4=",879579530348170701,4643929721705876290,-2095972828796515492,-2732523237241361668>()
         ),
         a(
            var0,
            (String)com.yiyiaddon.m.b.a<"skeka5tw0dwam","b7uyVcIP0eU+uGG/t0HJJddwg5FSRS88qIJvQdtnyQ1Lvg==",-5147735619808006154,5431499531097177861,-1806092231037698716,8209122285515052821>(),
            com.yiyiaddon.e.c.d.g.POISON_STORAGE,
            (String)com.yiyiaddon.m.b.a<"s3uwhen40u0mte","dWodfQuQdPoq3z57sRCKNbjDM5wm8FPh50CDh+65yjE=",3601960174524220822,1536402258655550385,-7097951638711139441,6127202193718179818>()
         )
      );
   }

   private static g.b a(com.yiyiaddon.e.c.a var0) {
      com.yiyiaddon.e.c.d.b var1 = var0.a(com.yiyiaddon.e.c.d.g.START);
      com.yiyiaddon.e.c.d.b var2 = var0.a(com.yiyiaddon.e.c.d.g.END);
      boolean var3 = var1 != null && var2 != null;
      String var4;
      String var5;
      if (var3) {
         var4 = String.format(
            (String)com.yiyiaddon.m.b.a<"s1uxd6tiv35mil","juK+I/chG08l8RVeNeARVZ7yCnbVA9/0kAyEGCz0xiaJeCKKZ8oqhmJF6P7iZimpRMmyG3aHJ4jS4tp/X7sO3WlERLdHPKogIUEgxdkveTO2q/5sofuifjHjlxTaRuf761Q=",5460354918056907188,-4194246960472386292,8067849460475608752,1018468950757714970>(),
            var1.a().getX(),
            var1.a().getZ(),
            var2.a().getX(),
            var2.a().getZ()
         );
         int var6 = Math.abs(var2.a().getX() - var1.a().getX()) + 1;
         int var7 = Math.abs(var2.a().getZ() - var1.a().getZ()) + 1;
         var5 = "" + var6 + var7 + var1.a().getY() + com.yiyiaddon.e.c.a.a(var1.a());
      } else if (var1 == null && var2 == null) {
         var4 = (String)com.yiyiaddon.m.b.a<"sgkjonv7mygx3","phqMbcyiSUIcoM57Gn10GlQB9wPjYwbIkq230yXur0YDTMaytofDKA==",-5178596562451515526,-4435314387497252887,354508179315747410,174183400074696230>();
         var5 = (String)com.yiyiaddon.m.b.a<"s2ckpqfs0y6kyu","6arZQ3YXCqMt339H1EscA+iFTa0F0gfRys3CVgETaiv8gw==",8243498202672610121,-4873916182430320020,4329626850266623477,-1860495478241854335>();
      } else {
         var4 = (String)com.yiyiaddon.m.b.a<"sifw9c4xehk9s","SMx5anlNx1ZOIAp/WRQI+gvbLtzRyYxC3VLVBpnzOPkbYE3pTQJ5quqgQ7goDmZYQ9Sl6F6IPyCRco43G0Th6A==",5896651698782434150,-7537063869383933839,8113676789827354218,-2182406411963733753>();
         var5 = (String)com.yiyiaddon.m.b.a<"s2ckpqfs0y6kyu","6arZQ3YXCqMt339H1EscA+iFTa0F0gfRys3CVgETaiv8gw==",8243498202672610121,-4873916182430320020,4329626850266623477,-1860495478241854335>();
      }

      com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(
         (
               var3
                  ? (String)com.yiyiaddon.m.b.a<"s18h45dkb2zitf","1dj53hYt+syVy/DQ0hbNCkItAnbYrc/T2ocFCAEYlhY=",4734695364480712395,4154325492886971512,3296158445953436019,-8721418039766812158>()
                  : (String)com.yiyiaddon.m.b.a<"s2enpb6r2xwglw","GYUWw3I3l2TuQbEGT6xTaGOhHxOCd19WuDzsVO/0HVI=",1525910959269773336,-4958614933981829493,-4723154687215125743,-291041632719036857>()
            )
            + "",
         () -> {
            String var1x = var0.a().b(com.yiyiaddon.e.c.d.g.START);
            if (var1x != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s48nlxujgg07m","YZhJDJ9MDswUsg3m276+jGZ+9AMI2cRONyNy7T+uIhE=",462689930395742952,7614181031182232948,-550499664899598429,3453519021440004527>()) {
                  case -114700078:
                     com.yiyiaddon.d.d.b(
                        (String)com.yiyiaddon.m.b.a<"s2668g69atozb4","YE9bhVJNNBZQnHlc0ATuMyk8y84W5aGdx/8fVISgliexX6Em",2594573114983862185,69628954393722547,3887583077299445924,-2369426957946823187>(),
                        var1x + ""
                     );
                     return;
                  default:
                     throw null;
               }
            } else {
               Minecraft var2x = Minecraft.getInstance();
               if (var2x != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lac1qey53ie5","sRXgRIjNWPDJFXfYD5aEl+5rK7yxY+D2qBymgJSbZMU=",-9212824810446151875,1533437864380264644,3108648790329531542,8365235207375868052>()) {
                     case 160110959:
                        var2x.setScreen(null);
                        switch ((int)com.yiyiaddon.m.b.a<"s1a2o59rt94vbu","G+AzXHtFhjlK0ePWmSUX/yldOJfjHpmAGTHdLt77VQ0=",-8812782876434490962,-7430397871490603870,8068498250205869185,1264601439182692633>()) {
                           case 739641952:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
            }
         }
      );
      com.yiyiaddon.l.j.a var9 = new com.yiyiaddon.l.j.a(
         (String)com.yiyiaddon.m.b.a<"s1365q2ym5b189","U2+MH3aNux8c+p/zscawFWVjbXg1orB9pQbDY1XXnzbZkZ3U",4542292230503128867,6836884495662005701,7501713364013422507,8595356272612126434>(),
         () -> {
            com.yiyiaddon.e.c.a.a.d(com.yiyiaddon.e.c.d.g.START);
            com.yiyiaddon.e.c.a.a.d(com.yiyiaddon.e.c.d.g.END);
            Minecraft var0x = Minecraft.getInstance();
            if (var0x != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3di6bm83d51pb","8MG/1HDM08Fb/PsMaoRg4KuVsd0dCPmagIv4X7g6AE4=",1484841581658885346,-282291133163412957,-486765778158437253,2642582650691545812>()) {
                  case -724234696:
                     var0x.setScreen(null);
                     switch ((int)com.yiyiaddon.m.b.a<"s1wddvs2nmw1c6","7H6XAC/alL3kGRKzpqWDLID6LWt7Zsi+EAX4yo8ePd8=",7610279957340381005,-110619011220025216,9186686239826084862,-3186118486547911385>()) {
                        case 852617472:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
         }
      );
      return new g.b(
            (String)com.yiyiaddon.m.b.a<"smgi0t1bre030","qwo04PQbRTg0jxC/BOQwbN7TIwpilJ38A/bvUI5BqIOjOaanRsF/QQ==",961661258541411458,-4689038810918188796,7208457030543060162,-2903717000725184073>(),
            var4,
            var5,
            List.of(List.of(var8), List.of(var9))
         )
         .a(() -> new ItemStack(Items.WHEAT_SEEDS));
   }

   private static g.b a(com.yiyiaddon.e.c.a var0, String var1, com.yiyiaddon.e.c.d.g var2, String var3) {
      com.yiyiaddon.e.c.d.b var4 = var0.a(var2);
      boolean var5 = var4 != null;
      String var6;
      String var7;
      if (var5) {
         var6 = String.format(
            (String)com.yiyiaddon.m.b.a<"s2w3xaa8429x3j","4tceBTasscp8JTkO00MXZx75Ba4Pj7BuAGkSsJ2rxPrwFyAGqWmiZ2TeBGatRUmidH0bsx3BlQp0G81DzILLnOPhSIZ+ppfJ8sQ=",-4479945259777366124,-1867435018118519416,-4277568662290432111,4046730745264535821>(),
            var4.a().getX(),
            var4.a().getY(),
            var4.a().getZ()
         );
         var7 = com.yiyiaddon.e.c.a.a(var4.a()) + "";
      } else {
         var6 = (String)com.yiyiaddon.m.b.a<"sgkjonv7mygx3","phqMbcyiSUIcoM57Gn10GlQB9wPjYwbIkq230yXur0YDTMaytofDKA==",-5178596562451515526,-4435314387497252887,354508179315747410,174183400074696230>();
         var7 = (String)com.yiyiaddon.m.b.a<"s2ckpqfs0y6kyu","6arZQ3YXCqMt339H1EscA+iFTa0F0gfRys3CVgETaiv8gw==",8243498202672610121,-4873916182430320020,4329626850266623477,-1860495478241854335>();
      }

      com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(
         (
               var5
                  ? (String)com.yiyiaddon.m.b.a<"s18h45dkb2zitf","1dj53hYt+syVy/DQ0hbNCkItAnbYrc/T2ocFCAEYlhY=",4734695364480712395,4154325492886971512,3296158445953436019,-8721418039766812158>()
                  : (String)com.yiyiaddon.m.b.a<"s2enpb6r2xwglw","GYUWw3I3l2TuQbEGT6xTaGOhHxOCd19WuDzsVO/0HVI=",1525910959269773336,-4958614933981829493,-4723154687215125743,-291041632719036857>()
            )
            + "",
         () -> {
            String var2x = var0.a().b(var2);
            if (var2x != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2y23bf4p0jqyz","8H+iopvWgh/QdvdSa/0Uoyz1Y7H+x67v0cOuTdxPHBg=",4410143867983444847,8230767630649227710,-7796827112812265525,-1194980298436874204>()) {
                  case -1077358626:
                     com.yiyiaddon.d.d.b(
                        (String)com.yiyiaddon.m.b.a<"s2668g69atozb4","YE9bhVJNNBZQnHlc0ATuMyk8y84W5aGdx/8fVISgliexX6Em",2594573114983862185,69628954393722547,3887583077299445924,-2369426957946823187>(),
                        var2x + ""
                     );
                     return;
                  default:
                     throw null;
               }
            } else {
               Minecraft var3x = Minecraft.getInstance();
               if (var3x != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11skshayck8p8","HDUeG1Dpor/N2Wt9bWkBVEybAikqCBNDRepMuR1U/3o=",9028721607130695003,6996317565021912007,4052297876972837636,-5857378240812286145>()) {
                     case 440618835:
                        var3x.setScreen(null);
                        switch ((int)com.yiyiaddon.m.b.a<"s6fimghdvd9ly","OJAjfT/wIFzCBuQOGnEsmbS/2QJV+C1BDi0V/PafJ0k=",7596361109321344756,1454579190896247825,-660015901695071220,-1762862111850116231>()) {
                           case -522284806:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
            }
         }
      );
      com.yiyiaddon.l.j.a var9 = new com.yiyiaddon.l.j.a(
         (String)com.yiyiaddon.m.b.a<"s1365q2ym5b189","U2+MH3aNux8c+p/zscawFWVjbXg1orB9pQbDY1XXnzbZkZ3U",4542292230503128867,6836884495662005701,7501713364013422507,8595356272612126434>(),
         () -> {
            com.yiyiaddon.e.c.a.a.d(var2);
            Minecraft var1x = Minecraft.getInstance();
            if (var1x != null) {
               switch ((int)com.yiyiaddon.m.b.a<"so46kbvodma0n","2VjAZrRaDBpiqhLof1w5SpH0PFhvf/pUeuTsVpIN/Pc=",6640872653913032279,-7265083059729778496,-3434852329635061862,-2746700854100313613>()) {
                  case 626806376:
                     var1x.setScreen(null);
                     switch ((int)com.yiyiaddon.m.b.a<"s33ezptpcndu3q","ulRY29jpVgSIzs9oIVhZJUnxzs7UMmNprm5I1kiHgUM=",8270724446359930676,1769078590750350147,8747723145790989495,3519998641405981268>()) {
                        case 944834359:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
         }
      );
      return new g.b(var3 + var1, var6, var7, List.of(List.of(var8), List.of(var9))).a(() -> a(var2));
   }

   private static ItemStack a(com.yiyiaddon.e.c.d.g var0) {
      switch (var0) {
         case START:
         case END:
            ItemStack var1 = new ItemStack(Items.WHEAT_SEEDS);
            switch ((int)com.yiyiaddon.m.b.a<"s2hgzbi95i5s0t","keA+poznKLfUQOfNpS8zDs1DBJ4/3G9PSDs2N2JX1+s=",-5085226203334401453,-2720739594219788495,4003474606512193915,-5545856307420878285>()) {
               case -1342532301:
                  return var1;
               default:
                  throw null;
            }
         case SINGLE_STORAGE:
         case MULTI_STORAGE:
         case SEED_STORAGE:
         case POISON_STORAGE:
            ItemStack var10000 = new ItemStack(Items.CHEST);
            switch ((int)com.yiyiaddon.m.b.a<"s3vqutwk8wzhqa","4dPVw+WP281Q3ZtsMuZ0zXDzKQUwZCz2ZKnX2YOHLWQ=",-4053867797594743984,1756743719282340526,-3112463818820394448,5313081247019258322>()) {
               case 22603034:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }
}
