package com.yiyiaddon.l.a;

import io.github.humbleui.skija.Canvas;

public final class b {
   private static final float eL = 0.09F;
   private static final float eM = 0.2F;
   private static final float eN = 0.03F;
   private float bd;
   private float eO;
   private float eP;
   private float eQ;
   private float eR;
   private boolean fC;
   private boolean fD;
   private boolean fE;

   public void jJ() {
      this.eO = this.bd;
      this.fC = true;
      this.fD = false;
      this.eP = 0.0F;
      this.fE = false;
   }

   public void jK() {
      if (this.fD) {
         switch ((int)com.yiyiaddon.m.b.a<"s9k1wcqtb1qg9","fkEo3qRIwgbjAPUaF5C/OQ/EJgyO6ViMquSfcu6hWvY=",-7614390159691153784,-760367417518494769,-5892015518190954204,-541601344794734801>()) {
            case -547019880:
               return;
            default:
               throw null;
         }
      } else {
         if (!this.fC) {
            switch ((int)com.yiyiaddon.m.b.a<"s2qhrnlwtof0vc","6YaCq3Zai6ykwoNJlYyDXu+xv77n8vt4/A35Acqaun8=",9157398264828636727,5878802471238177919,1600810900657916548,-178387656885447958>()) {
               case 909554534:
                  if (!this.fD) {
                     switch ((int)com.yiyiaddon.m.b.a<"sqv9u6hsq96f0","yb/Al/bsXoqYfkto9bRHooZDJjyhNe+7gwzCHdY/qmw=",4354690314443705020,6139756742220552042,7406447444847724324,4366347347599701629>()) {
                        case -1830421564:
                           this.fE = false;
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

         this.fC = false;
         this.fD = true;
         this.eQ = 0.0F;
         this.eR = this.bd;
         this.fE = false;
      }
   }

   public void jL() {
      this.jJ();
      this.fE = true;
   }

   public void i() {
      this.bd = 0.0F;
      this.eP = 0.0F;
      this.eQ = 0.0F;
      this.eR = 0.0F;
      this.fC = false;
      this.fD = false;
      this.fE = false;
   }

   public boolean fP() {
      if (!this.fC) {
         switch ((int)com.yiyiaddon.m.b.a<"s1843nep2cfkr","WKdUcQon8IuyLw6HahBioOokgtfD1kRoR+hAlt5QCHI=",-2043165677618035046,-5881649139169639810,6325992753759573167,6840412674651234354>()) {
            case 388407834:
               if (!this.fD) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2gb68oy28ku5v","4G28sLa9D33ltKV93vjyUg8Qiq0M32omljAWwczB3fI=",-1198646909573287849,-247917821119958121,-833809207344652043,6180580342981570318>()) {
                     case 517284126:
                        switch ((int)com.yiyiaddon.m.b.a<"s2s9jjjbcgs1o2","I030ThTyi24bLDoJGQdisruzHyHQALTODMc+P4xXTaU=",-1422256340303089783,7044288217952163691,-8658908247301347410,-6185338957449307916>()) {
                           case 1362477762:
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

      switch ((int)com.yiyiaddon.m.b.a<"s336trg7ojj6cy","f05hm21IZ0Ds8yL3SlqmR1yqTUU24S1GFqhZPihD99I=",-7860011864736510854,-7157968701043210065,-2164755233846099941,-4215910898467924516>()) {
         case 2087812270:
            return false;
         default:
            throw null;
      }
   }

   public float p() {
      return 1.0F - 0.03F * this.bd;
   }

   public float f() {
      return this.bd;
   }

   public boolean a(Canvas var1, float var2, float var3) {
      float var4 = this.p();
      if (Math.abs(var4 - 1.0F) < 5.0E-4F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fp25c25mtxcb","NzYhHx7R2pYzeBx1AgwY0Lym+K8EI8Zpq33nZGH9KPQ=",-177969621431663596,5366970992787162656,5212698481394125724,-7454469011628017968>()) {
            case 2106632064:
               return false;
            default:
               throw null;
         }
      } else {
         var1.save();
         var1.translate(var2, var3);
         var1.scale(var4, var4);
         var1.translate(-var2, -var3);
         return true;
      }
   }

   public boolean a(Canvas var1, float var2, float var3, float var4, float var5) {
      return this.a(var1, var2 + var4 / 2.0F, var3 + var5 / 2.0F);
   }

   public void a(float var1) {
      float var10000;
      if (Float.isFinite(var1)) {
         label49:
         switch ((int)com.yiyiaddon.m.b.a<"s3vdv7mpc7memk","w4C9eB+TYf5D+QYqKL5/xc+Pp9CGSP82i6GRZeXT188=",-2621724848459764625,-7687202454711398414,-4206503601888023373,-6038801007949112877>()) {
            case -430949230:
               var10000 = Math.max(0.0F, var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2pg6rwypja0z6","t++dQVJtZw1zdjKWPn3BI+3hsCBVezICg2JAg7Sg0zg=",7446200191207142126,-7736843866724431146,-7131428342509857303,8186486277116396994>()) {
                  case -755166156:
                     break label49;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s1xv8wqib87wqi","x34gd+E/EoYLHUeDjcC6kFciFHZYgrnBDra++4+hl0w=",-3393486883129905993,4596037057897285281,-5597502198254220845,-4839930334962099769>()) {
            case -2129157146:
               break;
            default:
               throw null;
         }
      }

      var1 = var10000;
      if (this.fC) {
         switch ((int)com.yiyiaddon.m.b.a<"s2puttld2gpf1h","xnRW85zyQP/NCf9kNpkyPDg+xNYODqa/wVwmijFwmG8=",1884504041898763598,-4989315852958368505,5952822266522889173,6083414450735983438>()) {
            case 1894814694:
               this.eP += var1;
               this.bd = this.eO + (1.0F - this.eO) * a.e(Math.min(1.0F, this.eP / 0.09F));
               if (this.fE) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3p006wqrlb1he","qUcCmJ6OrPcah/WBWbC3PzozQOrsHnwCctjSIqc/3DM=",7729091216554353453,-2064524380320575717,2800043441066952474,9147043082629469286>()) {
                     case -1956176887:
                        if (this.eP >= 0.09F) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1aypa58vusuq1","/x8sVCDypKCFFWRkMQimeg8YrYPo7z67PELXi9arUzQ=",1330924022006365152,-8586485687296750040,-8879856762943130434,-8552870450798580471>()) {
                              case 1323142128:
                                 this.fC = false;
                                 this.fD = true;
                                 this.eQ = 0.0F;
                                 this.eR = this.bd;
                                 this.fE = false;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1hvkrdiuvx707","5js3OqKSvbtNB9efyn20nlSF0s1jxC1NXDAaByz1ZZ8=",8504742340297092655,-4484851460774604221,-1152655668916978258,-3389743702011737145>()) {
                                    case -1661000236:
                                       return;
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
               break;
            default:
               throw null;
         }
      } else if (this.fD) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i4nn1a2wyvya","c//3axzHcqMgI/07rrUP4wbR7aXGMliup1FzexlWvZA=",-4810426560471271268,5328695148197939895,8103384926592947745,7813501691157236794>()) {
            case -430266109:
               this.eQ += var1;
               float var2 = Math.min(1.0F, this.eQ / 0.2F);
               this.bd = this.eR * (1.0F - a.e(var2));
               if (var2 >= 1.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3tkq70vticz40","hyexQ9dZ7A8SynqZTYrSzTbiTB4pw/ZeZBy3wPsEBGc=",2424648243259880513,-3212318301569143352,4166747283320267892,-3712928389344984659>()) {
                     case -2027415858:
                        this.fD = false;
                        this.bd = 0.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s3rki6f8xln26x","AJ/ffVruAPPd6XpfKmHMVC8xkeylNb2Io+3AuQKFV1E=",6879379155352992829,-4077193252492871920,-9191659921782761168,1993547348638589257>()) {
                           case 101580920:
                              return;
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
   }
}
