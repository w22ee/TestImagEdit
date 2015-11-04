package com.example.administrator.testimagedit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.core.utils.image.BitmapHelper;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.impl.components.edit.TuEditTurnAndCutFragment;
import org.lasque.tusdk.impl.components.edit.TuEditTurnAndCutOption;
import org.lasque.tusdk.modules.components.TuSdkComponent;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

public class MainActivity extends Activity  implements TuEditTurnAndCutFragment.TuEditTurnAndCutFragmentDelegate {

    private RadioGroup sex_chose_group;
    public TuSdkHelperComponent componentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sex_chose_group = (RadioGroup)findViewById(R.id.sex_chose_group);
        this.componentHelper = new TuSdkHelperComponent(MainActivity.this);
        sex_chose_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                TuSdkGeeV1.albumCommponent(MainActivity.this, new TuSdkComponent.TuSdkComponentDelegate() {
                    @Override
                    public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment) {
                        openEditMultiple(result, error, lastFragment);
                    }
                }).showComponent();

//                showSimple(MainActivity.this);
            }
        });
    }


    public void showSimple(Activity activity)
    {
        if (activity == null) return;

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditTurnAndCutOption.html
        TuEditTurnAndCutOption option = new TuEditTurnAndCutOption();
        // 控制器类型
        // option.setComponentClazz(TuEditTurnAndCutFragment.class);

        // 设置根视图布局资源ID
        // option.setRootViewLayoutId(TuEditTurnAndCutFragment.getLayoutId());

        // 保存到临时文件 (默认不保存, 当设置为true时, TuSdkResult.imageFile, 处理完成后将自动清理原始图片)
        // option.setSaveToTemp(false);

        // 保存到系统相册 (默认不保存, 当设置为true时, TuSdkResult.sqlInfo, 处理完成后将自动清理原始图片)
        // option.setSaveToAlbum(true);

        // 保存到系统相册的相册名称
        // option.setSaveToAlbumName("TuSdk");

        // 照片输出压缩率 (默认:90，0-100 如果设置为0 将保存为PNG格式)
        // option.setOutputCompress(90);

        // 是否开启滤镜支持 (默认: 关闭)
        option.setEnableFilters(true);

        // 开启用户滤镜历史记录
        option.setEnableFiltersHistory(true);

        // 开启在线滤镜
        option.setEnableOnlineFilter(true);

        // 显示滤镜标题视图
        option.setDisplayFiltersSubtitles(true);

        // 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜)
        // option.setFilterGroup(new ArrayList<String>());

        // 需要裁剪的长宽
        // option.setCutSize(new TuSdkSize(640, 640));

        // 是否显示处理结果预览图 (默认：关闭，调试时可以开启)
        // option.setShowResultPreview(false);

        // 滤镜组行视图宽度 (单位:DP)
        // option.setGroupFilterCellWidthDP(60);

        // 滤镜组选择栏高度 (单位:DP)
        // option.setFilterBarHeightDP(80);

        // 滤镜分组列表行视图布局资源ID (默认:
        // tusdk_impl_component_widget_group_filter_group_view，如需自定义请继承自
        // GroupFilterGroupView)
        // option.setGroupTableCellLayoutId(GroupFilterGroupView.getLayoutId());

        // 滤镜列表行视图布局资源ID (默认:
        // tusdk_impl_component_widget_group_filter_item_view，如需自定义请继承自
        // GroupFilterItemView)
        // option.setFilterTableCellLayoutId(GroupFilterItemView.getLayoutId());

        // 是否在控制器结束后自动删除临时文件
        option.setAutoRemoveTemp(true);

        TuEditTurnAndCutFragment fragment = option.fragment();

        // 输入的图片对象 (处理优先级: Image > TempFilePath > ImageSqlInfo)
        fragment.setImage(BitmapHelper.getRawBitmap(activity, R.raw.sample_photo));

        // 输入的临时文件目录 (处理优先级: Image > TempFilePath > ImageSqlInfo)
        // editFragment.setTempFilePath(result.imageFile);

        // 输入的相册图片对象 (处理优先级: Image > TempFilePath > ImageSqlInfo)
        // editFragment.setImageSqlInfo(result.imageSqlInfo);

        fragment.setDelegate(this);

        // see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
        this.componentHelper = new TuSdkHelperComponent(activity);
        // 开启相机
        this.componentHelper.presentModalNavigationActivity(fragment);
    }

    /** 开启多功能图片编辑 */
    private void openEditMultiple(TuSdkResult result, Error error, TuFragment lastFragment)
    {
        if (result == null || error != null){
            return;
        }

        // 组件委托
        TuSdkComponent.TuSdkComponentDelegate delegate = new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
            {
                TLog.d("onEditMultipleComponentReaded: %s | %s", result, error);
            }
        };

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditMultipleComponent.html
        TuEditMultipleComponent component = null;

        if (lastFragment == null)
        {
            component = TuSdkGeeV1.editMultipleCommponent(this.componentHelper.activity(), delegate);
        }
        else
        {
            component = TuSdkGeeV1.editMultipleCommponent(lastFragment, delegate);
        }

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditMultipleComponentOption.html
        // component.componentOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditMultipleOption.html
         component.componentOption().editMultipleOption();

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditCuterOption.html
        // component.componentOption().editCuterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditFilterOption.html
        // component.componentOption().editFilterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditSkinOption.html
        // component.componentOption().editSkinOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/sticker/TuEditStickerOption.html
        // component.componentOption().editStickerOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditAdjustOption.html
        // component.componentOption().editAdjustOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditSharpnessOption.html
        // component.componentOption().editSharpnessOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditApertureOption.html
        // component.componentOption().editApertureOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditVignetteOption.html
        // component.componentOption().editVignetteOption()

        // 设置图片
        component.setImage(result.image)
                // 设置系统照片
                .setImageSqlInfo(result.imageSqlInfo)
                        // 设置临时文件
                .setTempFilePath(result.imageFile)
                        // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                        // 开启组件
                .showComponent();
    }



    /**
     * 图片编辑完成
     *
     * @param fragment
     *            旋转和裁剪视图控制器
     * @param result
     *            旋转和裁剪视图控制器处理结果
     */
    @Override
    public void onTuEditTurnAndCutFragmentEdited(TuEditTurnAndCutFragment fragment, TuSdkResult result)
    {
        if (!fragment.isShowResultPreview())
        {
            fragment.hubDismissRightNow();
            fragment.dismissActivityWithAnim();
        }
        TLog.d("onTuEditTurnAndCutFragmentEdited: %s", result);
    }

    /**
     * 图片编辑完成 (异步方法)
     *
     * @param fragment
     *            旋转和裁剪视图控制器
     * @param result
     *            旋转和裁剪视图控制器处理结果
     * @return 是否截断默认处理逻辑 (默认: false, 设置为True时使用自定义处理逻辑)
     */
    @Override
    public boolean onTuEditTurnAndCutFragmentEditedAsync(TuEditTurnAndCutFragment fragment, TuSdkResult result)
    {
        TLog.d("onTuEditTurnAndCutFragmentEditedAsync: %s", result);
        return false;
    }

    @Override
    public void onComponentError(TuFragment fragment, TuSdkResult result, Error error)
    {
        TLog.d("onComponentError: fragment - %s, result - %s, error - %s", fragment, result, error);
    }

}
