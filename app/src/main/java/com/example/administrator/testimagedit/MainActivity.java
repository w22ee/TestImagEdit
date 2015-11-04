package com.example.administrator.testimagedit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

public class MainActivity extends Activity   {

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
                TuSdkGeeV1.albumMultipleCommponent(MainActivity.this, new TuSdkComponent.TuSdkComponentDelegate() {
                    @Override
                    public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment) {
                        openEditAdvanced(result, error, lastFragment);
                    }
                }).showComponent();

            }
        });
    }


    /** 开启图片高级编辑 */
    private void openEditAdvanced(TuSdkResult result, Error error, TuFragment lastFragment)
    {
        if (result == null || error != null) return;

        // 组件委托
        TuSdkComponent.TuSdkComponentDelegate delegate = new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
            {
                TLog.d("onEditAdvancedComponentReaded: %s | %s", result, error);
            }
        };

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditComponent.html
        TuEditComponent component = null;

        if (lastFragment == null)
        {
            component = TuSdkGeeV1.editCommponent(this.componentHelper.activity(), delegate);
        }
        else
        {
            component = TuSdkGeeV1.editCommponent(lastFragment, delegate);
        }

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditComponentOption.html
        // component.componentOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditEntryOption.html
        // component.componentOption().editEntryOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditCuterOption.html
        // component.componentOption().editCuterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditFilterOption.html
        // component.componentOption().editFilterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/sticker/TuStickerChooseOption.html
        // component.componentOption().editStickerOption()

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

}
