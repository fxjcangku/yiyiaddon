package com.yiyiaddon.e.f.b;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public final class b {
   private static final Set<EntityType<?>> r = Set.of(
      EntityType.AREA_EFFECT_CLOUD,
      EntityType.ARROW,
      EntityType.FALLING_BLOCK,
      EntityType.FIREWORK_ROCKET,
      EntityType.ITEM,
      EntityType.LLAMA_SPIT,
      EntityType.SPECTRAL_ARROW,
      EntityType.ENDER_PEARL,
      EntityType.EXPERIENCE_BOTTLE,
      EntityType.SPLASH_POTION,
      EntityType.LINGERING_POTION,
      EntityType.TRIDENT,
      EntityType.LIGHTNING_BOLT,
      EntityType.FISHING_BOBBER,
      EntityType.EXPERIENCE_ORB,
      EntityType.EGG
   );
   private static List<EntityType<?>> W;

   private b() {
   }

   public static List<EntityType<?>> z() {
      if (W == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u7lxk75ifttm","Y5qtOsSyi0dyjJw4q2hk5onx6rbY6x5AnT75zPxGjYk=",-2514257415721893192,1354480836628636958,-5336109581454555561,731745030626937744>()) {
            case -500219648:
               ArrayList var0 = new ArrayList();
               Iterator var1 = BuiltInRegistries.ENTITY_TYPE.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"srkcjjoghe5jw","Op4TyCaMmrV3msUS58Socv/EjBark++p88fgXajI9t0=",-3240233977398218652,100793849724275217,4797657558141231587,8559508304978752339>()) {
                  case 1767885406:
                     while (var1.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3fgp1mb4p71km","zPVjUK8a44i5oYHqHVFECk7i1Xs0HtBQBHAEVG1el0U=",3196947317689686402,4123103781163626943,-8888166114435379926,1572294449275903782>()) {
                           case -1343315465:
                              EntityType var2 = (EntityType)var1.next();
                              if (r.contains(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3uoanvbybp7ui","Q7rQFALWfkil0V9lEMsarHTOm0utAtxP/ratO+Mdn1Q=",2378057559964743496,-7617554602442959320,-7468983260295740008,-4530725592514893566>()) {
                                    case -673811956:
                                       switch ((int)com.yiyiaddon.m.b.a<"s154ptj294zp4q","XKTNOp4Gbre4mMWMjkYFyUrOSzAHyWGURH5KFx8+4b8=",635772091346690070,3259027063292853189,2832547145353765394,-4596815323653398904>()) {
                                          case -783167633:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var0.add(var2);
                                 switch ((int)com.yiyiaddon.m.b.a<"sth9wagqjnwyg","TxTqSLIPSNA5qkhg7r6npf9AHJKiciPKpistZoJhm7c=",8801213878759529833,5381422550265867920,7465829082450357018,-4165468668892014965>()) {
                                    case 66053643:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }

                     W = List.copyOf(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"s3ijcmx1dor33q","Lm8kQ9bYUoNrW9grMfonRLIlk/Wm/89hDweyoTQqEvk=",-8685251983434808502,3291961190646273975,6468171408239848846,-6125267083567415584>()) {
                        case -1618255032:
                           return W;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return W;
      }
   }
}
