package com.yiyiaddon.e.b.e;

import java.util.Comparator;
import java.util.List;
import net.minecraft.core.BlockPos;

public final class b {
   private b() {
   }

   public static com.yiyiaddon.g.a.a a(List<com.yiyiaddon.g.a.a> var0, BlockPos var1, com.yiyiaddon.j.a.b var2, long var3) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2obqq3uaa6vev","vxxv+UT1Vb2Bqn9ILzRu9VHHiuJIGRAuUGG9k0p4lSA=",7188201304500584617,5028912631725914202,2367996048940159491,-357146253431593667>()) {
            case 2115834237:
               if (!var0.isEmpty()) {
                  return var0.stream()
                     .filter(com.yiyiaddon.g.a.a::G)
                     .filter(
                        var3x -> {
                           if (!var2.a(var3x.a(), var3x.bU(), var3x.fH(), var3)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s33rzbef76x7u7","sABH8GnKK8Ln2tbxnCQZGdGqRx3FVq4/kLcZY07YJgA=",7978701219559773624,575017441116706581,-1313889073080586862,-1421279002245385944>()) {
                                 case -1184804740:
                                    switch ((int)com.yiyiaddon.m.b.a<"sm13c3ievitv2","ZGb1LGvd/fusSoO5Bq5yIv1ZiQtFgqAfFrFwl1umzD4=",615642680685344102,2990388889029199580,7588055779587131690,60662460630535503>()) {
                                       case 201060907:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s2bgxbij6ceegy","NPSuUJKGwOqBUIYeFhck7YRge7IaEMMWhoM09nW0jRY=",6997231263717231504,8922115794737390310,-1095053955790447117,9654646764844747>()) {
                                 case -921846351:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }
                     )
                     .min(Comparator.comparingDouble(var1x -> var1x.f(var1)))
                     .orElse(null);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sheiruwjraq2t","2M+ZfKUiiM15Z7xQNeityb1orJn4oIIPwKpeqMV5U/g=",6127428721000203350,-4669415567413444153,-1622090835662588212,-1121326476102010145>()) {
                     case -891492502:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }
}
