package com.yiyiaddon.e.n.i;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record r(String td, Map<String, String> ad, List<String> bO, List<String> bP, int mC, Map<String, String> ae) {
   public static final int mD = 1;

   public r(String var1, Map<String, String> var2, List<String> var3, List<String> var4, int var5) {
      this(var1, var2, var3, var4, var5, Map.of());
   }

   public static r a(String var0) {
      return new r(var0, new LinkedHashMap<>(), List.of(), List.of(), 1);
   }

   public String aA(String var1) {
      if (this.ad != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ebgc8kwuh4gv","QRxSYYlSimT/8plUBe28++KD0zYSHyyYPcZsWQj+wP8=",-897012465397113680,-4536897411616177045,2285634917567764804,-7602612581511798004>()) {
            case 1178090129:
               if (this.ad.containsKey(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1x3mz86qlr814","TcQsY7Ut8ksp0axZs3haCBROHGJ9sK7ebrtPnD3dsgQ=",-5441596640393627741,-3965214234195653759,-1538759856859153077,3905242676086768952>()) {
                     case -1388450820:
                        return this.ad.get(var1);
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.ae == null) {
         switch ((int)com.yiyiaddon.m.b.a<"snhuyxne2cu4z","8uwAdJpz2drdm/aaE4l3xWMF/Y2IxJij0nwBcHZDD5E=",1631391616670883372,-2778337557121113855,-6034662208730087107,-5720601251363693815>()) {
            case -938249875:
               switch ((int)com.yiyiaddon.m.b.a<"s36t9usrjl3br5","MOCp1qRqLugIyznVqlzYuw5HGmSNgTHzcioNnAUd2lM=",8810576291242306240,8122610895014654224,-3922034102234220474,-6296094933908707777>()) {
                  case -689437619:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = this.ae.get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1ujuwpx718ut7","k8C97rRYUP/W4xF9GovE9a5gMJ1o2gbdjqrqC9/oqPg=",5454495248238565125,-7019336673989093642,-4443856972092553714,-2694360086955933490>()) {
            case 93344171:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public c b(String var1) {
      if (this.ad != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s178ztfx7k52al","gJ0TJeZwH3yJri5zlcb0Z7xnB7s4WEJqRvVqiC3HEak=",7919490949928868373,-9117954654638268168,-2778029521067277796,-6128479750908916995>()) {
            case -150549890:
               if (this.ad.containsKey(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2vcugqn6t0glg","lmu/l57JjhEmEvwMepPaxO9Aw5+kSyjclYs2IFjwpx4=",-1006111246398121240,1570145019534981410,6146853207521530300,-8425429984222160717>()) {
                     case 883281004:
                        return c.VERIFIED;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.ae != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pr36cb9nspgp","6suf95g1d4z8ATkC4Lnt54frDohhCfKDDagnTcG3xXs=",5379507657860025631,3276459122784488748,4704401870232693628,1003333288947973503>()) {
            case -761876600:
               if (this.ae.containsKey(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ykul6vcm35ja","BO7FxRkY1gpx/OYb/rx/qJM1qTcxFGakBujDoNzKc5o=",415908705985061117,-604938952033913115,5217319806442513469,-2386681508915347510>()) {
                     case 541846466:
                        c var10000 = c.DOCUMENTED;
                        switch ((int)com.yiyiaddon.m.b.a<"sf3n49kgl028k","W8iMR8vLl9hpu1T3bAGJU+5F92OmEfXtZk2j/+wGXsI=",-3349476484121631306,-2153076247586104930,-1875543415485085633,-3989063373448747486>()) {
                           case -1505629188:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2l2any1jrn1ke","iQEDRYCCBYbP41Mu4A6bIW3brtqoLKuz8IZFLhLdZbQ=",7324706936159824432,-4518092837529200480,2262464242132780803,9085329683252713594>()) {
         case 1041049471:
            return null;
         default:
            throw null;
      }
   }

   public boolean ad(String var1) {
      if (this.bO != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37yldlxm9es5b","Gyb9Ko3cIEWRnpvjlsZZXAJ2PqslNcXbPZht4Ar5MBQ=",1730120555833412585,6680944783459484816,-4053001516447919847,2127417439534043597>()) {
            case 987639630:
               if (this.bO.contains(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2b1qqyqs8wplv","IhNLLcX4apZcYmaHgVapOWPQcunSdNKBKKRRXE1wUHc=",4542049657425625470,-8678402403308944003,-8306134490496553102,5534399527980286847>()) {
                     case 1351370774:
                        return true;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public boolean ae(String var1) {
      if (this.bP != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sned0q3tep3t1","+WwLOQOCJ+hLwoBELEO8I1e3d3l5CKOTHGsHRF1pGes=",-1444398290483042165,-7724817575055380375,-7414015569407521278,3575292106276951694>()) {
            case -1259256471:
               if (this.bP.contains(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3c8m1ntekohs0","67rjozzS+1OusqbbBX9UmxDTxqdLevwLeVjjeu5SuZA=",4458985650727188950,6825149885742709679,672742375957071986,-995457387938481964>()) {
                     case 2027641103:
                        return true;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public int bO() {
      if (this.ad == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3j0sq80q8kxd4","3eCvp/wQbzUF6PAjBZjYmmnKQUzBkN4ZtikZdUHwytU=",-3138724283712589659,-928087259823674458,-8190663582421601799,-4077594990361008108>()) {
            case -1109053585:
               switch ((int)com.yiyiaddon.m.b.a<"s1gcehprlvfjha","Ae6Or9m23/lqCvFOSV5MaBRFz4GRQgPKa57yEB6qFhs=",6628594347964871953,7530532025245440457,8206362296135267517,-9010790765891089118>()) {
                  case -95678950:
                     return 0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = this.ad.size();
         switch ((int)com.yiyiaddon.m.b.a<"s1p8mshk8gm6lk","pTsJOS1iUNcy+BghdOjxPRtpyiECePp4TW/EO2Ezx+E=",-7063771243052217871,9132919500055499156,8207494934241857319,-4981782308911148588>()) {
            case 1956113239:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public String bT() {
      return this.td;
   }

   public Map<String, String> n() {
      return this.ad;
   }

   public List<String> aX() {
      return this.bO;
   }

   public List<String> aY() {
      return this.bP;
   }

   public int ce() {
      return this.mC;
   }

   public Map<String, String> q() {
      return this.ae;
   }
}
