package com.by.controller.admin;

import com.by.po.Type;
import com.by.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/types")
    public String list(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        try{
            PageHelper.startPage(pageNum, 3);
            List<Type> allType = typeService.listType();
            //得到分页结果对象
            PageInfo<Type> pageInfo = new PageInfo<>(allType);
            model.addAttribute("pageInfo", pageInfo);
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--list:"+e.getMessage());
        }
        return "admin/types";
    }
    /*
      @Valid:校验注解,BindingResult:校验结果返回类
    * */
    @GetMapping("/types/input")
    public String input(Model model){
        try{
            //页面初始化，防止前端页面空指针
            model.addAttribute("type",new Type());
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--g-input:"+e.getMessage());
        }
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        try{
            //查出来再改
            model.addAttribute("type",typeService.getType(id));
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--g-editInput:"+e.getMessage());
        }
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String postType(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        try{
            Type type1 = typeService.getTypeByName(type.getName());
            if(type1 != null){
                result.rejectValue("name","nameError","名称重复！");
            }
            if(result.hasErrors()){
                return "admin/types-input";
            }
            int t = typeService.saveType(type);
            if(t == 0){
                attributes.addFlashAttribute("message","新增失败！");
            }else{
                attributes.addFlashAttribute("message","新增成功");
            }
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--g-postType:"+e.getMessage());
        }
        return "redirect:/admin/types";
    }
    @PostMapping("/types/{id}")
    public String editPostType(@Valid Type type, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        try{
            Type type1 = typeService.getTypeByName(type.getName());
            if(type1 != null){
                result.rejectValue("name","nameError","名称重复！");
            }
            if(result.hasErrors()){
                return "admin/types-input";
            }
            int t = typeService.updateType(id,type);
            if(t == 0){
                attributes.addFlashAttribute("message","更新失败！");
            }else{
                attributes.addFlashAttribute("message","更新成功");
            }
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--g-editPostType:"+e.getMessage());
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        try{
            typeService.deleteType(id);
            attributes.addFlashAttribute("message","删除成功！");
        }catch (Exception e){
            logger.info("Admin-TypeController-Exception--g-editPostType:"+e.getMessage());
        }
        return "redirect:/admin/types";
    }
}
