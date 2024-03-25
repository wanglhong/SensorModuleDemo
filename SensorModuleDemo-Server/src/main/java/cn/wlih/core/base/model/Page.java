package cn.wlih.core.base.model;

import cn.wlih.core.util.MyModelUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(defaultValue = "分页数据")
public class Page<M, MVO> {

    @Schema(defaultValue = "当前页")
    private Integer pageNum = 1;
    @Schema(defaultValue = "每页数量")
    private Integer pageSize = 10;
    @Schema(defaultValue = "总条数")
    private Long total = 0L;
    @Schema(defaultValue = "数据信息")
    private List<MVO> data;

    public static <M, MVO> Page<M, MVO> setPageInfo(Page<M, MVO> page, List<M> list, Class<MVO> modelVoClass) {
        PageInfo<M> pageInfo = new PageInfo<M>(list);
        page.setTotal(pageInfo.getTotal());
        List<MVO> listVo = new ArrayList<>();
        for (M m1 : list) {
            listVo.add(MyModelUtil.copyTo(m1, modelVoClass));
        }
        page.setData(listVo);
        return page;
    }
}
