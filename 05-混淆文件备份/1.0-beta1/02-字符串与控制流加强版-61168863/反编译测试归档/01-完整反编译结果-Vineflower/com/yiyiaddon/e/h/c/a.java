package com.yiyiaddon.e.h.c;

import java.util.List;

public record a(com.yiyiaddon.e.h.c.a.a a, boolean bw, String kK, List<com.yiyiaddon.e.h.c.a.b> aV, String kL, String kM) {
   public static com.yiyiaddon.e.h.c.a a(com.yiyiaddon.e.h.c.a.a var0, String var1, List<com.yiyiaddon.e.h.c.a.b> var2, String var3, String var4) {
      return new com.yiyiaddon.e.h.c.a(var0, true, var1, List.copyOf(var2), var3, var4);
   }

   public static com.yiyiaddon.e.h.c.a a(com.yiyiaddon.e.h.c.a.a var0, String var1) {
      return new com.yiyiaddon.e.h.c.a(
         var0,
         false,
         var0.m() + "",
         List.of(
            new com.yiyiaddon.e.h.c.a.b(
               (String)com.yiyiaddon.m.b.a<"s2sodngy098ev0","vWI5KG16WA/fwlJRlVkZpzw2uTSRxRFweehqMCmP1ak=",6065040761447978201,4928540887067079783,-3276034080320296707,6221226157701318433>(),
               var1
            )
         ),
         null,
         null
      );
   }

   public boolean be() {
      if (this.kL != null) {
         switch ((int)com.yiyiaddon.m.b.a<"so9i9fuy8oohr","gDnZh8mSccA0T9Pc7x2ZLplTat+EWI1mggdQ0GwLUDU=",-4173590148677646791,-5755309029918756753,-5936101678163214241,1522410934357938500>()) {
            case 672717023:
               if (!this.kL.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1eou4ysu30amz","+aaNuWm2VF9UranT5y8bfOFaoCAph7TQo63g8F88CCE=",-1801193242354027605,-4009320252973108454,5340491653585993649,-7523036275407801188>()) {
                     case -944623787:
                        switch ((int)com.yiyiaddon.m.b.a<"s1ce3qjqod5bcc","ZYVs9LkXjDg1S3r/h/5yhxPPCcSmTzmSBUCkBMVkUlg=",-6770353562345425215,2271302435965741074,-6208086187463755888,-6830210633759087824>()) {
                           case 1163823288:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s3dv8y8yhzg8md","rRCpU1JMxs6rgPfKyuSzewlp7CJHu+ZhKuH1lYTwo4E=",-2089911470973897486,5855866672305202414,118253024083242900,56826911731234554>()) {
         case 734599884:
            return false;
         default:
            throw null;
      }
   }

   public boolean bf() {
      if (this.kM != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ifxg37s0y84t","bvHzxICRB4MAXIcawuFV/vcAYCQmCOoOmsADiJtv6sw=",-995493868549624586,4472801331490739521,7471590102217991140,1956468164536555770>()) {
            case -1766121378:
               if (!this.kM.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1gv3ushnao5kc","h0X6MuTMy1bdU+URemX1PLt/DALsjjSvl9ub1JcupFU=",-8339738743346535323,5725238633194310812,-7524811458813384037,2713833148447035663>()) {
                     case 2054001347:
                        switch ((int)com.yiyiaddon.m.b.a<"s27iw0bsg190e4","XfrKDFKpjB5RYyNPSrwWjPn7eCXP0u2M7xSUj8+r4L4=",4462638082807159209,-4544469650439369702,-8964240328881361725,-839477642760165755>()) {
                           case -616074237:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2vpye6jcs939v","s9D/1CnhvHODvtetTBYMN9mPRioWFeFk4BP8PA36pDk=",-7070428042625833845,2625440559152816613,-5809887381351978231,-5252247166261708744>()) {
         case 1818518820:
            return false;
         default:
            throw null;
      }
   }

   public boolean bg() {
      return this.bw;
   }

   public String D() {
      return this.kK;
   }

   public List<com.yiyiaddon.e.h.c.a.b> ab() {
      return this.aV;
   }

   public String bg() {
      return this.kL;
   }

   public String bh() {
      return this.kM;
   }

   public enum a {
      ITEM(
         (String)com.yiyiaddon.m.b.a<"s2f6yhsavmlzlu","FN7zM8oajfgZnb5+PvxuSeDEo3AA3ytyXeKwcUGznu4=",1049906823128885643,-3612269913842484706,-230078666802348695,5267655340800703905>()
      ),
      BLOCK(
         (String)com.yiyiaddon.m.b.a<"s2cemtzmhknlu9","3Ogh77zH0GX/N0q8C6VhuotRTfHhUdXIuwxh6/XjYUA=",-6457096606357845977,772344163530174244,2012248935446883896,5123766528163518559>()
      ),
      ENTITY(
         (String)com.yiyiaddon.m.b.a<"s15ex1ucyyhtcs","eXiEk37Vvh45kWzRzeYySE4qoLx/MMgNZlDmivbwj+o=",-4963461397371610463,911413899179364305,2960832622096897414,5633055188226374128>()
      );

      private final String kN;

      a(String var3) {
         this.kN = var3;
      }

      public String m() {
         return this.kN;
      }
   }

   public record b(String kO, String kP) {
      public String h() {
         return this.kO;
      }

      public String bi() {
         return this.kP;
      }
   }
}
