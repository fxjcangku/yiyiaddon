package com.yiyiaddon.e.k.a;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.e.k.b.c;
import com.yiyiaddon.e.k.b.e;
import com.yiyiaddon.l.g.a.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class a {
   public static final int kK = 1;
   public static final int kL = 6;
   public static final int kM = 0;
   public static final int kN = 20;
   private static final int kO = 0;
   private static final int kP = 1;
   private static final int kQ = 6;
   private static final int kR = 10;
   private static final int kS = 255;
   private static final int kT = 10;
   private static final int kU = 255;
   private static final int kV = 255;
   public volatile e a = e.AIM;
   public com.yiyiaddon.e.k.b.a a = com.yiyiaddon.e.k.b.a.INSTANT;
   public int df = 4;
   public final List<String> bm = new ArrayList<>();
   public int kW = 1;
   public boolean cY = true;
   public boolean aS = true;
   public boolean cZ = true;
   public boolean da;
   public boolean bV;
   public boolean aJ = true;
   public boolean db = true;
   public com.yiyiaddon.e.k.b.b a = com.yiyiaddon.e.k.b.b.BOTH;
   public boolean dc = true;
   public final d t = new d();
   public final d u = new d();
   public final d v = new d();
   public final d w = new d();
   public boolean dd = true;
   public final d x = new d();
   public c a = c.PERCENT;

   public a() {
      this.t.b(com.yiyiaddon.l.g.a.b.q(0)).c(10);
      this.u.b(com.yiyiaddon.l.g.a.b.q(0)).c(255);
      this.v.b(com.yiyiaddon.l.g.a.b.q(1)).c(10);
      this.w.b(com.yiyiaddon.l.g.a.b.q(1)).c(255);
      this.x.b(com.yiyiaddon.l.g.a.b.q(6)).c(255);
   }

   public void d(JsonObject var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1f8dh302raes8","9syJhr2YbDL+Ca5d1OMZ1lvmTARk1K9t8PJpmH+xBqc=",1902403950515776767,-2932379353991842541,193903551596858695,-9214344046787469965>()) {
            case -819738662:
               return;
            default:
               throw null;
         }
      } else {
         this.a = a(
            var1,
            (String)com.yiyiaddon.m.b.a<"s156z62tza0ybb","scdWlaCzduFKfsNyHhYY1TWlPDX4PqZcvfFm443Y9zi/iu6R",931577194171734435,5931531475989024142,7668731131849457647,-1574178907503606323>(),
            e.class,
            this.a
         );
         this.a = a(
            var1,
            (String)com.yiyiaddon.m.b.a<"s3q3ir1yvnrq5p","eWbLKdsOKv/qjXMrWlcOXqd4+skGX9CbrdXfBgqOjIAGFgdi",1815067410627258575,4221650944690393597,1026948920426602805,3257071999015188997>(),
            com.yiyiaddon.e.k.b.a.class,
            this.a
         );
         this.df = a(
            b(
               var1,
               (String)com.yiyiaddon.m.b.a<"s257zsjelmuf1m","aG0QTEgMW74CjDxFhF0ABMiVf0RLmWDwvWfwMZe3v7lmo9Jr",-4655266199822547774,9177570743408353950,1978963367565019463,5694016551996588626>(),
               this.df
            ),
            1,
            6
         );
         a(
            this.bm,
            a(
               var1,
               (String)com.yiyiaddon.m.b.a<"s27y2i00c2fgpg","jHCA7DUhXJqhngEcAhOisoNIpM6//WKxUtjpfFLqj6eaOoi3",7793917899334307648,-1692852967103175887,-7313840503733154721,-1694567352177711073>()
            )
         );
         this.kW = a(
            b(
               var1,
               (String)com.yiyiaddon.m.b.a<"s1op8ae14azjg1","+9TqB47G8tp/A8J71vReujxdcQB2xDHEYHDdig2AjZKyNRh9JEhWNJmPAJpWcyd5",8966006147755750918,4534758640998658741,8625350373943368223,-7483580651172277960>(),
               this.kW
            ),
            0,
            20
         );
         this.cY = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s3k48p6y0efgbc","lWl9hXb8BsUrf2HsYBrKqNNaQiliWG9wqW1fpjh5e0+klv/a",-1338541437760738856,-5669232318407100477,-1920501159749428960,-2456378650332954277>(),
            this.cY
         );
         this.aS = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"sedwn3n0ov6gt","6Dhnllibr7j5+oi6YVHzwvUwjXviVsh6EQ3VG2oE/9qf9gD+7hA=",-4793444688803687092,-6634987533965051197,-7957328235759700874,-7431060577413456209>(),
            this.aS
         );
         this.cZ = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s38de20oxvgf3y","LpB6RB5B/xPjF/t8mUNH8nhBW9EKgik8X3QPK2mvNSVJcwXf",921380053526115919,7984421850608861846,7740663943775013500,3215941586936630848>(),
            this.cZ
         );
         this.da = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s22ziewod4b6mh","Y3+c3bhTL3sta38DJmI/Oc2HonjU6MX7kuTcuODhsFijoZfO4bdAlw==",-165357754568467222,1985304964680279174,-8624469898014048912,-944217293132064517>(),
            this.da
         );
         this.bV = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s325fmczmjp252","EF66irBEGM4acwdQyCuhEbryuVHJ6JFtrIWTJdyN7Bvrd0OC+cM=",-8567792447620275211,-7162490405053283277,-8486982344529333875,-4792814204993230799>(),
            this.bV
         );
         this.aJ = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s3syt2k7ki0ax3","Bnf2oIKn9pYp89/elv0Y50mMoUm75+flZww2lMxf0V+cse9MRje+vjc3",3688777774179297230,-8991590751603403123,7420407457993002583,-7632981834426123214>(),
            this.aJ
         );
         this.db = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s2fzq6sefc2yxb","UIZnsCgaR+6TvwJqjfVovjrRQTheHPvzFjqHlr0lmG51fPme",6388570507635696935,1896905837239679467,-2657284280802565060,-7030390636785160221>(),
            this.db
         );
         this.a = a(
            var1,
            (String)com.yiyiaddon.m.b.a<"s1ys8ee329x928","2T4IAWH9TkWd9GUsokV5Dc0mRy8QMv5kjcmWKT1Z3TrTSEES",7473412716347805306,2726487860883569810,1791261376292819472,2380233216463494953>(),
            com.yiyiaddon.e.k.b.b.class,
            this.a
         );
         this.dc = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s2mugg435v3zz8","sxnExNb2tVwGnTAW+5dEJCRK8D/ogqclnxNl1CRjKCQNtCvu",3603101353970820045,-7700210252517852904,4132381513278226688,3729778177492744247>(),
            this.dc
         );
         this.t
            .b(
               var1,
               (String)com.yiyiaddon.m.b.a<"s22ht9rviiuzpn","XyaC+bdM2t5DEg4jyYVREFdQK3q38dYEzVHgtnsQLHHEedMnofjaOV4e",-5450833188851275226,-9105399388790294985,3785279558641871244,3991162629016274280>()
            );
         this.u
            .b(
               var1,
               (String)com.yiyiaddon.m.b.a<"si4vk3wxm2z69","hqgAO9tXk9wmLLelkC1xFqirc6mVzVReD4itBKk+LU6skA7rZW7ukVtk",-8880458471857915948,-7963782405108274527,-926609914903329217,8163477967206624728>()
            );
         this.v
            .b(
               var1,
               (String)com.yiyiaddon.m.b.a<"s15lpqivtytzvh","Ldo7w/945qfbgNlC0sBrEPkw2gz/j/IFVtOVbbEGUcYSqEJTyH9J6AoM",2248679965243286793,-8545065473095349869,-9133919216396340915,2448442960508669096>()
            );
         this.w
            .b(
               var1,
               (String)com.yiyiaddon.m.b.a<"sbbywkl9m59vx","CrC0r252Vjnqlj7WM8MBw/1E5ePByYiUhpY2vrXQNnFP6TqXSI+hZhIE",-1107628299323053881,3577391105523911355,-5944850829167394032,124741020584910010>()
            );
         this.dd = b(
            var1,
            (String)com.yiyiaddon.m.b.a<"s1ntv5shhptuc9","qJjVqX3PQg3wuSiILcvxASv5ahcjK5r8N/zyoHcm/s6/gt+RgcU=",1072295318415365299,-697268907015544293,8053139801884914911,-2528781234094071447>(),
            this.dd
         );
         this.x
            .b(
               var1,
               (String)com.yiyiaddon.m.b.a<"s1074xz5xqplv5","x1wCon04XeEKe+aqS0k31s6pNxnZvAxXSO2G/oPb3ULPpeoDaJY=",6450468846711313264,8356099034032129884,2697385878850463235,4949102741356968533>()
            );
         this.a = a(
            var1,
            (String)com.yiyiaddon.m.b.a<"ssqige31ydk8b","sJuh7EncnCRFxQwDt+K8T0DPv+o8PiovwLj2esEEgejXLjE1",4346596342560805982,7313682710408552733,-3148570747521262135,2243084688602678826>(),
            c.class,
            this.a
         );
      }
   }

   public void c(JsonObject var1) {
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s156z62tza0ybb","scdWlaCzduFKfsNyHhYY1TWlPDX4PqZcvfFm443Y9zi/iu6R",931577194171734435,5931531475989024142,7668731131849457647,-1574178907503606323>(),
         this.a.name()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3q3ir1yvnrq5p","eWbLKdsOKv/qjXMrWlcOXqd4+skGX9CbrdXfBgqOjIAGFgdi",1815067410627258575,4221650944690393597,1026948920426602805,3257071999015188997>(),
         this.a.name()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s257zsjelmuf1m","aG0QTEgMW74CjDxFhF0ABMiVf0RLmWDwvWfwMZe3v7lmo9Jr",-4655266199822547774,9177570743408353950,1978963367565019463,5694016551996588626>(),
         this.df
      );
      var1.add(
         (String)com.yiyiaddon.m.b.a<"s27y2i00c2fgpg","jHCA7DUhXJqhngEcAhOisoNIpM6//WKxUtjpfFLqj6eaOoi3",7793917899334307648,-1692852967103175887,-7313840503733154721,-1694567352177711073>(),
         b(this.bm)
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1op8ae14azjg1","+9TqB47G8tp/A8J71vReujxdcQB2xDHEYHDdig2AjZKyNRh9JEhWNJmPAJpWcyd5",8966006147755750918,4534758640998658741,8625350373943368223,-7483580651172277960>(),
         this.kW
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3k48p6y0efgbc","lWl9hXb8BsUrf2HsYBrKqNNaQiliWG9wqW1fpjh5e0+klv/a",-1338541437760738856,-5669232318407100477,-1920501159749428960,-2456378650332954277>(),
         this.cY
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sedwn3n0ov6gt","6Dhnllibr7j5+oi6YVHzwvUwjXviVsh6EQ3VG2oE/9qf9gD+7hA=",-4793444688803687092,-6634987533965051197,-7957328235759700874,-7431060577413456209>(),
         this.aS
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s38de20oxvgf3y","LpB6RB5B/xPjF/t8mUNH8nhBW9EKgik8X3QPK2mvNSVJcwXf",921380053526115919,7984421850608861846,7740663943775013500,3215941586936630848>(),
         this.cZ
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s22ziewod4b6mh","Y3+c3bhTL3sta38DJmI/Oc2HonjU6MX7kuTcuODhsFijoZfO4bdAlw==",-165357754568467222,1985304964680279174,-8624469898014048912,-944217293132064517>(),
         this.da
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s325fmczmjp252","EF66irBEGM4acwdQyCuhEbryuVHJ6JFtrIWTJdyN7Bvrd0OC+cM=",-8567792447620275211,-7162490405053283277,-8486982344529333875,-4792814204993230799>(),
         this.bV
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3syt2k7ki0ax3","Bnf2oIKn9pYp89/elv0Y50mMoUm75+flZww2lMxf0V+cse9MRje+vjc3",3688777774179297230,-8991590751603403123,7420407457993002583,-7632981834426123214>(),
         this.aJ
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2fzq6sefc2yxb","UIZnsCgaR+6TvwJqjfVovjrRQTheHPvzFjqHlr0lmG51fPme",6388570507635696935,1896905837239679467,-2657284280802565060,-7030390636785160221>(),
         this.db
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1ys8ee329x928","2T4IAWH9TkWd9GUsokV5Dc0mRy8QMv5kjcmWKT1Z3TrTSEES",7473412716347805306,2726487860883569810,1791261376292819472,2380233216463494953>(),
         this.a.name()
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2mugg435v3zz8","sxnExNb2tVwGnTAW+5dEJCRK8D/ogqclnxNl1CRjKCQNtCvu",3603101353970820045,-7700210252517852904,4132381513278226688,3729778177492744247>(),
         this.dc
      );
      this.t
         .c(
            var1,
            (String)com.yiyiaddon.m.b.a<"s22ht9rviiuzpn","XyaC+bdM2t5DEg4jyYVREFdQK3q38dYEzVHgtnsQLHHEedMnofjaOV4e",-5450833188851275226,-9105399388790294985,3785279558641871244,3991162629016274280>()
         );
      this.u
         .c(
            var1,
            (String)com.yiyiaddon.m.b.a<"si4vk3wxm2z69","hqgAO9tXk9wmLLelkC1xFqirc6mVzVReD4itBKk+LU6skA7rZW7ukVtk",-8880458471857915948,-7963782405108274527,-926609914903329217,8163477967206624728>()
         );
      this.v
         .c(
            var1,
            (String)com.yiyiaddon.m.b.a<"s15lpqivtytzvh","Ldo7w/945qfbgNlC0sBrEPkw2gz/j/IFVtOVbbEGUcYSqEJTyH9J6AoM",2248679965243286793,-8545065473095349869,-9133919216396340915,2448442960508669096>()
         );
      this.w
         .c(
            var1,
            (String)com.yiyiaddon.m.b.a<"sbbywkl9m59vx","CrC0r252Vjnqlj7WM8MBw/1E5ePByYiUhpY2vrXQNnFP6TqXSI+hZhIE",-1107628299323053881,3577391105523911355,-5944850829167394032,124741020584910010>()
         );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1ntv5shhptuc9","qJjVqX3PQg3wuSiILcvxASv5ahcjK5r8N/zyoHcm/s6/gt+RgcU=",1072295318415365299,-697268907015544293,8053139801884914911,-2528781234094071447>(),
         this.dd
      );
      this.x
         .c(
            var1,
            (String)com.yiyiaddon.m.b.a<"s1074xz5xqplv5","x1wCon04XeEKe+aqS0k31s6pNxnZvAxXSO2G/oPb3ULPpeoDaJY=",6450468846711313264,8356099034032129884,2697385878850463235,4949102741356968533>()
         );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"ssqige31ydk8b","sJuh7EncnCRFxQwDt+K8T0DPv+o8PiovwLj2esEEgejXLjE1",4346596342560805982,7313682710408552733,-3148570747521262135,2243084688602678826>(),
         this.a.name()
      );
   }

   private static void a(List<String> var0, List<String> var1) {
      var0.clear();
      var0.addAll(var1);
   }

   private static boolean b(JsonObject var0, String var1, boolean var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sizwzppo7tyyd","OMnQsP5YTjWOMVS0OGN2lKGuXYO2CYKWl5HCXP/aouA=",7645149568280535249,-6157293633826223499,-6426661543192868569,6726717153989294879>()) {
            case -1275593144:
               if (var3.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1osie0i6scuvt","fiQhUEhc81OAFYgQb98EAI2PkHTSMJIq4b3gIHpNYCM=",-1005023986420639856,1089394089971377137,4118275485370989834,-6370807837513955976>()) {
                     case -1342913635:
                        boolean var10000 = var3.getAsBoolean();
                        switch ((int)com.yiyiaddon.m.b.a<"s1jsm7oi9f7pfm","RqJlMJ848lhU8FBdwjNEDtxiVV3gemfY4indTgldl8s=",149299662719239856,5309982809607175958,-4247073559581576772,-4272736296027524184>()) {
                           case 1224758185:
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

      switch ((int)com.yiyiaddon.m.b.a<"s19msx9okm45ci","jw2CUmT/ceIBbNzRyCNnuNW3dzg7KdKoTuujoF0obb0=",-1609783320906228336,-551506328893279893,4282804814316382243,-3885563532438700036>()) {
         case -1766292557:
            return var2;
         default:
            throw null;
      }
   }

   private static int b(JsonObject var0, String var1, int var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s8dlwasjiw91p","gFAad6YTkfS4GHstsXcCV7T4wPuUVsi5KlEGhZtw2oY=",4670734586826133674,-1810761657740943396,5561041600023727422,8217531469107360340>()) {
            case -1388302036:
               if (var3.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3d8ehdxz0tmhl","KTzZJsKMk9NOMorkjcOjN63nBbD1cGipT/HRUnM4mAM=",-5959547519766167779,-3527245293419549838,2286831921275225880,8169256563556835229>()) {
                     case -973843877:
                        int var10000 = var3.getAsInt();
                        switch ((int)com.yiyiaddon.m.b.a<"s5itsvfifbrc4","KmjUXUkjxPZkZnMvjmb/nUSHJayuCu/6M8DxbteGmnU=",-287510834578119898,-1373868954562143532,-8420508739544493846,-1655829515257562313>()) {
                           case 1376897615:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2g55v23c4fc9z","da3lMpShCCONXsQybXalsernc6aTK7PBuoRzx/hs3D4=",-4337969865643253679,2786779760212370327,-2699212087407954934,1160936119940584836>()) {
         case 754650009:
            return var2;
         default:
            throw null;
      }
   }

   private static int a(int var0, int var1, int var2) {
      return Math.max(var1, Math.min(var2, var0));
   }

   private static <E extends Enum<E>> E a(JsonObject var0, String var1, Class<E> var2, E var3) {
      JsonElement var4 = var0.get(var1);
      if (var4 != null && var4.isJsonPrimitive()) {
         try {
            return Enum.valueOf(var2, var4.getAsString());
         } catch (IllegalArgumentException var6) {
            return (E)var3;
         }
      } else {
         return (E)var3;
      }
   }

   private static List<String> a(JsonObject var0, String var1) {
      ArrayList var2 = new ArrayList();
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lfyuwi8fsit9","4GxYpyLXfyFDxtvxQwZtLuEgnqimYAuw6oh4GLMWW6I=",8798295000544161645,6889913200804148600,-7934367724887915921,4789622997788458865>()) {
            case -2047353518:
               if (var3.isJsonArray()) {
                  Iterator var4 = var3.getAsJsonArray().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2wjsopz4zblfq","V7Ssa1fgKgW/zGBycXC9sWDG9Fd98JCvcRqMFynEfPA=",-2488552504442794708,589319436748597583,1852570300611716915,-1158826188514107502>()) {
                     case 518470425:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s25koj4fqibilb","/uJiTpbI2c6Cp8kWmiPavmIIEx6ujzP3PDfDi//xQWI=",-5222045040350268413,3995267084639246951,5737691365491587277,-5985007411470328568>()) {
                              case -1252103379:
                                 JsonElement var5 = (JsonElement)var4.next();
                                 if (var5 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s6ur3mg8778ev","svrrPRmS46d6+iI0RoB9uwzgrNJcsS7UzSGAXFWb/ho=",4092721848524238943,-7155998824220696026,-3453362044325303306,3390084925749663199>()) {
                                       case 478975322:
                                          if (var5.isJsonPrimitive()) {
                                             label35:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3vxk97jhq63z9","FmBwgfRQ4nerwyYF0hFHAUUKUHdVPs15kt5HoSlz4Ak=",-8531860419471526517,-472734438082373410,7910329924282310234,-8779205165394148828>()) {
                                                case 2017755945:
                                                   var2.add(var5.getAsString());
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2w3xx0nriu6f6","u0PX2h2zj0Xfz5Ov5rE7cot4Y9jQ0lzUPQC+Ih3Ffpc=",-8355122620589801857,-4476649653912457773,-6608344370244771743,372126681064291942>()) {
                                                      case -742411407:
                                                         break label35;
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s206gix4i0441y","RPiutqKxWMuBXlQ3O4anWayrx+1nmh7e0hk/VnyuOgY=",1696939839912507454,-5853168471474963035,8555580503940013528,-6338085254787413451>()) {
                                    case -1888169314:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3026lriizsreb","pCfqQOih9QCbRyUheEa2G7gXYGf6buvJhVb+nVYq9tM=",-6324513144884740921,4571807128563970032,-9147014539735908150,-7487167177025622595>()) {
                     case 617811502:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   private static JsonArray b(List<String> var0) {
      JsonArray var1 = new JsonArray();
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3hzeb29jzaza3","tI5tNM11S3ODNpaSi6i7icjyawDYEExQui8NoXUGF+k=",-7819033828437308558,-6459156747227707680,1706042949950135886,-8480252484172800250>()) {
         case 1823473866:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s38tpqhsdyber0","NZ8IgpjTn7m64635BxagAvo0Aj2YqsQP0/X38TCZiW0=",-5216649281918161671,3281918453668416851,1141247367532389544,-556734554660731655>()) {
                  case -1600804977:
                     String var3 = (String)var2.next();
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s4db4jykbqfxl","DfyrXW1jobF/+uJlDCAWK4mYFDm+w5NUci2FX80lE/w=",-1751728536254152362,-7696914576011370984,7321314516871998325,-2095720065108021051>()) {
                           case 883382380:
                              if (!var3.isBlank()) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"spamd4ptgw6z2","azyDEfn4OA1WJ2r2+t/Vlmlkf1TCKXhsbKkEhivHAp0=",3789445084229785973,8185629897044278383,-8593773259687688582,2013600251588718570>()) {
                                    case 354301171:
                                       var1.add(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3u56b7gn2qy0c","W73lh32uEXphJEPsOwfgdegEhi8WUanJ8JvenxBcbkQ=",4230980091509728843,-4970042260689646265,-5929237643614989154,-2597057721850289055>()) {
                                          case -360080020:
                                             break label28;
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3rt0lftem652t","sQsCaxmFzgoWVNC+rXYDVu4Gc6LhlIGJV3goCr4N808=",-2271976441287051538,-5828041510831941655,2882800978895602065,1237636616889862956>()) {
                        case -905907568:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }
}
