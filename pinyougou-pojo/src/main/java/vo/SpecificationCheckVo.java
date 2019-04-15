package vo;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationCheck;
import cn.itcast.core.pojo.specification.SpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格包装对象
 *
 * 组合对象
 */
public class SpecificationCheckVo implements Serializable{


    //规格对象  1
    private SpecificationCheck specificationCheck;
    //规格选项对象结果集 多
    private List<SpecificationOption> specificationOptionList;

    public SpecificationCheck getSpecificationCheck() {
        return specificationCheck;
    }

    public void setSpecificationCheck(SpecificationCheck specificationCheck) {
        this.specificationCheck = specificationCheck;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
