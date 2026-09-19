package com.yiyiaddon.e.j.g;

import com.yiyiaddon.m.b;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class a {
   private final Minecraft J = Minecraft.getInstance();
   private boolean m = true;
   private float bh = 1.0F;

   public void n(boolean var1) {
      this.m = var1;
   }

   public void b(float var1) {
      this.bh = Math.max(0.0F, Math.min(1.0F, var1));
   }

   private void c(float var1) {
      if (this.m) {
         switch ((int)b.a<"s370bi89n1y8oy","aCJUIT61FYx5h6oOziS3rNTY3/aIYRL96gxK/h5SQ5E=",3414686873009896391,3929231063861181917,6553380301006426665,4259698482404546154>()) {
            case -2050836802:
               if (this.J.player != null) {
                  switch ((int)b.a<"s2wa03t5xp76y2","Ae9WfQ52pN1v7lxxRbdpPJCrL4n9NHNMPdqbo5bU/YE=",364048556873890933,7346761506144496365,738050247590839568,3907057326542031453>()) {
                     case -1646209616:
                        if (this.J.level != null) {
                           this.J
                              .level
                              .playLocalSound(
                                 this.J.player.getX(),
                                 this.J.player.getY(),
                                 this.J.player.getZ(),
                                 SoundEvents.EXPERIENCE_ORB_PICKUP,
                                 SoundSource.PLAYERS,
                                 this.bh,
                                 var1,
                                 false
                              );
                           return;
                        }

                        switch ((int)b.a<"s3750ntyhht03k","VOx8ZEAUqt3Z51/crkFQ53r/pOFAkTbsOhIHIcoAENU=",-2940540708705342679,5617419258608032390,-2938638469203714633,1166940736339794133>()) {
                           case 1449563955:
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

   public void fj() {
      this.c(1.5F);
   }

   public void fk() {
      this.c(0.8F);
   }

   public void fl() {
      this.c(0.5F);
   }

   public void fm() {
      if (this.m) {
         switch ((int)b.a<"s2y6kws3rijvhr","jfRSx1im0xb7JJ2gCin3Ki5V+OcKxGYJqIX2t7CeigU=",-8126877182736290320,3718489602376659120,952064954301429000,-7739001939439488148>()) {
            case -1061846706:
               if (this.J.player != null) {
                  switch ((int)b.a<"s30cj5ouoe4u4n","ZWk7JgzFwpjo2c/DywrxhNSZbQy4NDw+9LpqOw8rdns=",3209257094911001051,-2203650024531516239,1928718644503557460,-789679826818080816>()) {
                     case 929092647:
                        if (this.J.level != null) {
                           this.J
                              .level
                              .playLocalSound(
                                 this.J.player.getX(),
                                 this.J.player.getY(),
                                 this.J.player.getZ(),
                                 SoundEvents.ANVIL_FALL,
                                 SoundSource.PLAYERS,
                                 this.bh,
                                 0.6F,
                                 false
                              );
                           return;
                        }

                        switch ((int)b.a<"so4cez6fqeyix","15JWZs1aK2d6BZVBKuNL852RQ8xj8J/JjP/bzXHYZwg=",6500851209219043223,-8890275130544311374,8979754808392862100,-3272052303175433597>()) {
                           case -1801456304:
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

   public void fn() {
      if (this.m) {
         switch ((int)b.a<"s35c8cipfkpd1p","s1LS1T+vL5SFEyQ55x8t/5j2EjsCTLyDbrZLfN0KCt0=",-8292516227633304658,-3995924921177999054,3750658083618465933,-3413764272425783005>()) {
            case -1121011221:
               if (this.J.player != null) {
                  switch ((int)b.a<"s4he0mjbicbfy","kM4GBBAHLOJd34bsNxmMfgttYYYkEAkPBYnVSZ7IUuc=",-4065362890840262654,1572428923464144290,12867059802162310,-3373678672950107827>()) {
                     case 525692133:
                        if (this.J.level != null) {
                           this.J
                              .level
                              .playLocalSound(
                                 this.J.player.getX(),
                                 this.J.player.getY(),
                                 this.J.player.getZ(),
                                 SoundEvents.WITHER_SPAWN,
                                 SoundSource.HOSTILE,
                                 this.bh * 0.5F,
                                 0.8F,
                                 false
                              );
                           return;
                        }

                        switch ((int)b.a<"s2sxwezwd65nnd","1/HUK40F8kggtscTHGr4FO2SeUoOPr+ke9El9hNAWfU=",-956306768428214693,-8904084698847354403,-2036693022855251335,-6317344905146346607>()) {
                           case 189491739:
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

   public void fo() {
      this.c(1.2F);
   }

   public void fp() {
      this.c(1.0F);
   }

   public void fq() {
      this.c(0.7F);
   }
}
